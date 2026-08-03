package day09;

import java.util.*;

public class ApplicationTags {
    private final Map<String , Set<String> > tagsByCompany = new HashMap<>();
    public void addTags(String company , String tag){
        addTagValidation(company,tag);
        String normalizedTag = tag.toLowerCase(Locale.ROOT);
        tagsByCompany.computeIfAbsent(company, c -> new HashSet<>()).add(normalizedTag);

    }
    public Set<String> getTags(String company){
        Set<String> tags = tagsByCompany.getOrDefault(company , Collections.emptySet());
        return Collections.unmodifiableSet(tags);
    }

    public boolean hasTag(String company , String tag){
        if (tag == null){
            return false;
        }
        String normalizedTag = tag.toLowerCase(Locale.ROOT);
        return getTags(company).contains(normalizedTag);
    }

    public Map<String  , Set<String>> viewAll(){
        Map<String, Set<String>> copy = new HashMap<>();
        tagsByCompany.forEach((company, tags) -> copy.put(company, Set.copyOf(tags)));
        return Map.copyOf(copy);
    }










    private static void addTagValidation(String company , String tags){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("Company must not be blank");
        }
        if (tags == null || tags.isBlank()){
            throw new IllegalArgumentException("Tag must not be blank");
        }
    }
}
