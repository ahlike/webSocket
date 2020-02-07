package src.like.server.core;

import java.util.HashSet;
import java.util.Set;

public class Mapping {
    private String name;
    private Set<String> patterns;

    public Mapping() {
        patterns = new HashSet<String>();
    }

    public Mapping(String name, Set<String> pattern) {
        this.name = name;
        this.patterns = pattern;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPattern(Set<String> pattern) {
        this.patterns = pattern;
    }
    public void addPattern(String pattern){
        this.patterns.add(pattern);
    }

    public String getName() {
        return name;
    }

    public Set<String> getPatterns() {
        return patterns;
    }
}
