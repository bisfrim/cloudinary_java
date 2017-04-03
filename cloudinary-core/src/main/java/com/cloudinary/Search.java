package com.cloudinary;

import com.cloudinary.api.ApiResponse;
import com.cloudinary.utils.ObjectUtils;
import org.cloudinary.json.JSONArray;
import org.cloudinary.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

public class Search {

    private JSONObject params;

    Search() {
        this.params = new JSONObject();
        this.params.put("sort_by", new JSONArray());
        this.params.put("aggregate", new JSONArray());
        this.params.put("includes", new JSONArray());
    }

    public Search expression(String value, Object... formats) {
        this.params.put("expression", String.format(value, formats));
        return this;
    }

    public Search maxResults(Integer value) {
        this.params.put("max_results", value);
        return this;
    }

    public Search nextCursor(String value) {
        this.params.put("next_cursor", value);
        return this;
    }

    public Search aggregate(String field) {
        JSONArray aggregate = this.params.getJSONArray("aggregate");
        aggregate.put(aggregate.length(), field);
        return this;
    }

    public Search includes(String field) {
        JSONArray includes = this.params.getJSONArray("includes");
        includes.put(includes.length(), field);
        return this;
    }

    public Search sortBy(String field, String dir) {
        JSONArray sortBy = this.params.getJSONArray("sort_by");
        JSONObject sortBucket = new JSONObject();
        sortBucket.put(field, dir);
        sortBy.put(sortBy.length(), sortBucket);
        return this;
    }

    public JSONObject toQuery() {
        return this.params;
    }

    public ApiResponse execute() throws Exception {
        Cloudinary cloudinary = new Cloudinary();
        Map<String, String> options = ObjectUtils.asMap("content_type", "json");
        Map<String, Object> paramsAsMap = ObjectUtils.toMap(this.params);
        return cloudinary.api().callApi(Api.HttpMethod.POST, Arrays.asList("resources", "search"), paramsAsMap, options);
    }
}