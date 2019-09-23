/**
 *  Copyright (C) 2017 Necati Caner Gaygisiz
 *  Copyright (C) 2019 Mehmet Sirin Usanmaz
 *
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.ui.SelectFromListDialog;
import managers.GradleManager;
import network.NetworkManager;
import network.model.response.MavenSearchResponse;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import util.ArrayHelper;
import util.Strings;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.concurrent.CompletableFuture;

public class SearchAction extends AnAction {

    private NetworkManager networkManager;

    private GradleManager gradleManager;

    private SelectFromListDialog.ToStringAspect toStringAspect;

    @Override
    public void actionPerformed(@NotNull AnActionEvent actionEvent) {
        Project project = actionEvent.getProject();

        if (project != null) {

            networkManager = new NetworkManager();

            toStringAspect = String::valueOf;

            gradleManager = new GradleManager(project);

            try {
                if (gradleManager.initBuildGradle()) {
                    doSearch(getSearchKeyword(), actionEvent);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                SwingUtilities.invokeLater(() -> Messages.showInfoMessage(e.getMessage(), Strings.ERROR_TITLE_FILE_NOT_FOUND));
            }
        }
    }

    private void doSearch(String keyword, AnActionEvent actionEvent) {
        if (StringUtils.isNotEmpty(keyword)) {
            getRepositories(keyword)
                    .thenAcceptAsync(mavenSearchResponse -> {
                                SwingUtilities.invokeLater(() -> {
                                    if (!mavenSearchResponse.getBody().getNumFound().equals("0")) {
                                        SelectFromListDialog dialog = initSelectFromListDialog(actionEvent.getProject(), Strings.TITLE_SELECT_REPOSITORY, ArrayHelper.docListToStringArray(mavenSearchResponse.getBody().getDocList()), toStringAspect);
                                        boolean isOk = dialog.showAndGet();
                                        if (isOk) {
                                            gradleManager.addDependency(String.valueOf(dialog.getSelection()[0]), actionEvent);
                                        }
                                    } else if (mavenSearchResponse.getSpellcheck().getSuggestion().size() > 0) {
                                        Messages.showInfoMessage(Strings.MESSAGE_SUGGESTIONS + String.join(",", ArrayHelper.objectListToStringArray(mavenSearchResponse.getSpellcheck().getSuggestion())), Strings.ERROR_TITLE_REPOSITORY_NOT_FOUND);
                                    } else {
                                        Messages.showInfoMessage("", Strings.ERROR_TITLE_REPOSITORY_NOT_FOUND);
                                    }
                                });
                            }
                    ).exceptionally(throwable -> {
                SwingUtilities.invokeLater(() -> {
                    Messages.showInfoMessage(throwable.getMessage(), Strings.ERROR_TITLE_MAVEN_SEARCH);
                });

                return null;
            });
        }
    }

    private CompletableFuture<MavenSearchResponse> getRepositories(String keyword) {
        return networkManager.getMavenSearchService().searchRepository(keyword, "json");
    }

    private String getSearchKeyword() {
        return Messages.showInputDialog(Strings.MESSAGE_MAVEN_SEARCH, Strings.TITLE_MAVEN_SEARCH, null);
    }

    private SelectFromListDialog initSelectFromListDialog(Project project, String title, String[] strings, SelectFromListDialog.ToStringAspect toStringAspect) {
        return new SelectFromListDialog(project, strings, toStringAspect, title, ListSelectionModel.SINGLE_SELECTION);
    }
}
