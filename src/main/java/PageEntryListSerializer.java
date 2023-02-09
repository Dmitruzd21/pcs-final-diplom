import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class PageEntryListSerializer {
    List<PageEntry> list;

    public PageEntryListSerializer(List<PageEntry> list) {
        this.list = list;
    }

    public String convertToJson() {
        GsonBuilder jsonBuilder = new GsonBuilder();
        Gson gson = jsonBuilder.create();
        return gson.toJson(list.toArray());
    }
}
