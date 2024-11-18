package com.github.sergey_guzov.pages.pastebin.settings;

public enum SyntaxHighlighting {

    BASH("Bash");
    private String syntax;
    SyntaxHighlighting (String syntax) {

        this.syntax = syntax;
    }
    public String getSyntax() {
        return syntax;
    }
}
