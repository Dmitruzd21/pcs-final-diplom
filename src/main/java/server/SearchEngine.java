package server;

import server.page.PageEntry;

import java.util.List;

public interface SearchEngine {
    List<PageEntry> search(String word);
}