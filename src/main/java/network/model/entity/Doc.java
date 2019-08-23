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
package network.model.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doc {
    private String id;

    @SerializedName("g")
    private String groupId;

    @SerializedName("a")
    private String artifactId;

    private String latestVersion;

    private String repositoryId;

    @SerializedName("p")
    private String packaging;

    private String timestamp;

    private String versionCount;

    @SerializedName("text")
    private List<String> textList;

    @SerializedName("ec")
    private List<String> ecList;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getLatestVersion() {
        return this.latestVersion;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryId() {
        return this.repositoryId;
    }

    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    public String getPackaging() {
        return this.packaging;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestamp() {
        return this.timestamp;
    }

    public void setVersionCount(String versionCount) {
        this.versionCount = versionCount;
    }

    public String getVersionCount() {
        return this.versionCount;
    }

    public void setTextList(List<String> textList) {
        this.textList = textList;
    }

    public List<String> getTextList() {
        return this.textList;
    }

    public void setEcList(List<String> ecList) {
        this.ecList = ecList;
    }

    public List<String> getEcList() {
        return this.ecList;
    }
}
