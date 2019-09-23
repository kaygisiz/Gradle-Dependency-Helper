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

import network.model.entity.Doc;

import java.util.List;
import java.util.stream.Stream;

public class ArrayHelper {
    public static String[] objectListToStringArray(List<?> obcejtList) {
        return Stream.of(obcejtList.toArray())
                .map(String::valueOf)
                .toArray(String[]::new);
    }

    public static String[] docListToStringArray(List<Doc> docList) {
        return Stream.of(docList.toArray())
                .map(o -> docToString((Doc) o))
                .toArray(String[]::new);
    }

    private static String docToString(Doc doc) {
        return doc.getGroupId() + ":" + doc.getArtifactId() + ":" + doc.getLatestVersion();
    }
}
