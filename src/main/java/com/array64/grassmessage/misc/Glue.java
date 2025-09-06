package com.array64.grassmessage.misc;

public enum Glue {
    /** If <code>&lt;glue/&gt;</code> has been encountered between two elements */ TRUE,
    /** If <code>&lt;glue/&gt;</code> hasn't been encountered between two elements but no whitespace has either */ DEFAULT,
    /** If <code>&lt;glue/&gt;</code> hasn't been encountered between two elements and whitespace has */ FALSE
}
