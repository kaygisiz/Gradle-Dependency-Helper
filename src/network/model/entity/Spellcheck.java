/**
 *  Copyright (C) 2017 Necati Caner Gaygisiz
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

import java.util.List;

public class Spellcheck {

    private String endOffset;

    private List<String> suggestion;

    private String numFound;

    private String startOffset;

    public String getEndOffset() {
        return endOffset;
    }

    public void setEndOffset(String endOffset) {
        this.endOffset = endOffset;
    }

    public List<String> getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(List<String> suggestion) {
        this.suggestion = suggestion;
    }

    public String getNumFound() {
        return numFound;
    }

    public void setNumFound(String numFound) {
        this.numFound = numFound;
    }

    public String getStartOffset() {
        return startOffset;
    }

    public void setStartOffset(String startOffset) {
        this.startOffset = startOffset;
    }
}
