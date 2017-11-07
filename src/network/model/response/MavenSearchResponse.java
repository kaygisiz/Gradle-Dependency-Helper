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
package network.model.response;

import com.google.gson.annotations.SerializedName;
import network.model.entity.*;

import java.util.List;

public class MavenSearchResponse {
    @SerializedName("responseHeader")
    private Header header;

    @SerializedName("response")
    private Body body;

    private Spellcheck spellcheck;

    public void setHeader(Header header) {
        this.header = header;
    }

    public Header getHeader() {
        return this.header;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Body getBody() {
        return this.body;
    }

    public void setSpellcheck(Spellcheck spellcheck) {
        this.spellcheck = spellcheck;
    }

    public Spellcheck getSpellcheck() {
        return this.spellcheck;
    }

    public class Header {
        private String status;

        @SerializedName("QTime")
        private String queryTime;

        @SerializedName("params")
        private Param paramList;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public void setQueryTime(String queryTime) {
            this.queryTime = queryTime;
        }

        public String getQueryTime() {
            return this.queryTime;
        }

        public void setParamList(Param paramList) {
            this.paramList = paramList;
        }

        public Param getParamList() {
            return this.paramList;
        }
    }

    public class Body {
        private String numFound;

        private String start;

        @SerializedName("docs")
        private List<Doc> docList;

        public void setNumFound(String numFound) {
            this.numFound = numFound;
        }

        public String getNumFound() {
            return this.numFound;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getStart() {
            return this.start;
        }

        public void setDocList(List<Doc> docList) {
            this.docList = docList;
        }

        public List<Doc> getDocList() {
            return this.docList;
        }
    }

}
