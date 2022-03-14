package repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class RepositoryParserFactory {

   private static final Map<String, RepositoryParser> propertiesCache = new HashMap<>();

   public static RepositoryParser getRepository(String repositoryFileName) {

      RepositoryParser repositoryParser = propertiesCache.get(repositoryFileName);
      if (repositoryParser != null) {
         return repositoryParser;
      }

      try (InputStream systemResourceAsStream = ClassLoader.getSystemResourceAsStream(repositoryFileName)) {

         Properties properties = new Properties();
         properties.load(systemResourceAsStream);
         RepositoryParser newRepositoryParser = new RepositoryParser(repositoryFileName, properties);
         propertiesCache.put(repositoryFileName, newRepositoryParser);
         return newRepositoryParser;
      } catch (IOException e) {
         throw new RuntimeException("Unable to load: " + repositoryFileName, e);
      }
   }

}
