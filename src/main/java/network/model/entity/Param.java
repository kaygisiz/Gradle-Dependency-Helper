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

public class Param {
    private String spellcheck;

    private String fl;

    private String sort;

    private String indent;

    @SerializedName("q")
    private String query;

    private String qf;

    @SerializedName("spellcheck.count")
    private String spellcheck_count;

    private String wt;

    private String rows;

    private String version;

    private String defType;

    public void setSpellcheck(String spellcheck) {
        this.spellcheck = spellcheck;
    }

    public String getSpellcheck() {
        return this.spellcheck;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFl() {
        return this.fl;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getSort() {
        return this.sort;
    }

    public void setIndent(String indent) {
        this.indent = indent;
    }

    public String getIndent() {
        return this.indent;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQf(String qf) {
        this.qf = qf;
    }

    public String getQf() {
        return this.qf;
    }

    public String getSpellcheck_count() {
        return spellcheck_count;
    }

    public void setSpellcheck_count(String spellcheck_count) {
        this.spellcheck_count = spellcheck_count;
    }

    public void setWt(String wt) {
        this.wt = wt;
    }

    public String getWt() {
        return this.wt;
    }

    public void setRows(String rows) {
        this.rows = rows;
    }

    public String getRows() {
        return this.rows;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return this.version;
    }

    public void setDefType(String defType) {
        this.defType = defType;
    }

    public String getDefType() {
        return this.defType;
    }
}
