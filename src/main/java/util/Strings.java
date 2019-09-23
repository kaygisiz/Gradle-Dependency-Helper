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
package util;

public class Strings {
    public static final String BASE_URL = "http://search.maven.org/";

    // Titles
    public static final String TITLE_SELECT_REPOSITORY = "Select Repository";
    public static final String TITLE_MAVEN_SEARCH = "Search Repository";

    // Messages
    public static final String MESSAGE_MAVEN_SEARCH = "Enter keyword to find repository: ";
    public static final String MESSAGE_SUGGESTIONS = "Similar repositories: ";

    // Error Titles
    public static final String ERROR_TITLE_MAVEN_SEARCH = "FAILED";
    public static final String ERROR_TITLE_REPOSITORY_NOT_FOUND = "Repository Not Found";
    public static final String ERROR_TITLE_FILE_NOT_FOUND = "File Not Found";
    public static final String ERROR_TITLE_SYNC_FAILED = "SYNC FALIED";


    // Error Messages
    public static final String ERROR_BASE_PATH_NOT_FOUND = "Project base path not found.";
    public static final String ERROR_PROJECT_BASE_DIR_NOT_FOUND = "Project base directory not found.";
    public static final String ERROR_BUILD_GRADLE_NOT_FOUND = "Project doesn't contain any gradle file.";
    public static final String ERROR_SYNC_FAILED = "Project sync failed.";
}
