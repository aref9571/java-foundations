package day09;

import java.util.*;

public class ApplicationTags {
    private final Map<String , Set<String>> tagsByCompany = new HashMap<>();
    public void addTags(String company , String tag){
        if (company == null || company.isBlank()){
            throw new IllegalArgumentException("Company must not be blank");
        }
        if (tag == null || tag.isBlank()){
            throw new IllegalArgumentException("Tag must not be blank");
        }
        String normalizeTage = tag.toLowerCase();
        tagsByCompany.computeIfAbsent(company , c -> new  HashSet<>()).add(normalizeTage);
    }

    public Set<String> getTags(String company){
        Set<String> tags = tagsByCompany.getOrDefault(company, Collections.emptySet());
        return Collections.unmodifiableSet(tags);
    }

    public boolean hasTag(String company , String tag){
        if (tag == null){
            return false;
        }
        String normalized = tag.toLowerCase();
        return getTags(company).contains(normalized);
    }

    public Map<String , Set<String>> viewAll(){
        return new HashMap<>(tagsByCompany);
    }
}
