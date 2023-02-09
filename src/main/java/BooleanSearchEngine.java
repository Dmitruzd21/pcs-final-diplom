import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {
    Map<String, List<PageEntry>> wordsStatistics = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        for (File pdfFile : pdfsDir.listFiles()) {
            String pdfName = pdfFile.getName();
            var doc = new PdfDocument(new PdfReader(pdfFile));
            for (int i = 1; i < doc.getNumberOfPages(); i++) {
                int pageNumber = i;
                var page = doc.getPage(i);
                var text = PdfTextExtractor.getTextFromPage(page);
                var words = text.split("\\P{IsAlphabetic}+");
                Map<String, Integer> freqsInOnePage = new HashMap<>();
                for (String word : words) {
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqsInOnePage.put(word, freqsInOnePage.getOrDefault(word, 0) + 1);
                }
                freqsInOnePage.forEach((word, freq) ->
                        {
                            if (wordsStatistics.get(word) == null) {
                                List<PageEntry> initialList = new ArrayList<>();
                                initialList.add(new PageEntry(pdfName, pageNumber, freq));
                                wordsStatistics.put(word, initialList);
                            } else {
                                List<PageEntry> listWithContent = wordsStatistics.get(word);
                                listWithContent.add(new PageEntry(pdfName, pageNumber, freq));
                                wordsStatistics.put(word, listWithContent);
                            }
                        }
                );
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        List<PageEntry> list = wordsStatistics.get(word.toLowerCase());
        Collections.sort(list);
        return list;
    }
}
