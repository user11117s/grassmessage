package com.array64.grassmessage.internal.components.concrete;

import com.array64.grassmessage.internal.components.GAbstractComponent;
import com.array64.grassmessage.internal.components.GComponent;
import com.array64.grassmessage.internal.components.InstantiationContext;
import net.kyori.adventure.text.Component;
import org.xml.sax.Attributes;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GEmbeddedMessage extends GAbstractComponent {
    private final String ref;
    private static final Pattern PATTERN_VARMAP = Pattern.compile("(([a-zA-Z0-9_-]+)\\s*=\\s*([a-zA-Z0-9_-]+)|(\\*))\\s*;");
    private static final int    GROUPNO_ASTERISK = 4,
                                GROUPNO_SOURCE = 3,
                                GROUPNO_DESTINATION = 2;

    private final List<VarmapStatement> stmts = new ArrayList<>();

    public GEmbeddedMessage(String ref, String varmap) {
        this.ref = ref;
        Matcher matcher = PATTERN_VARMAP.matcher(varmap);

        while(matcher.find()) {
            stmts.add(matcher.group(GROUPNO_ASTERISK) != null
                ? VarmapStatement.all()
                : VarmapStatement.individual(matcher.group(GROUPNO_SOURCE), matcher.group(GROUPNO_DESTINATION))
            );
        }
    }

    @Override
    protected void enterTag(String qName, Attributes attrs) {
        throwOnEnterTag();
    }

    @Override
    protected void exitTag(String qName) {
        throwOnExitTag();
    }

    @Override
    public Component instantiateInParent(Component parent, InstantiationContext ctx) {
        GComponent msg = Objects.requireNonNull(ctx.getMessage(ref), "Message " + ref + " could not be found.");

        Map<String, Object> oldVars = ctx.getVars(),
                            newVars = new HashMap<>();

        stmts.forEach(stmt -> {
            if(stmt.isAll())
                newVars.putAll(oldVars);
            else
                newVars.put(stmt.dst, oldVars.get(stmt.src));
        });
        InstantiationContext newCtx = new InstantiationContext(ctx.getFileData(), newVars);
        return msg.instantiateInParent(parent, newCtx);
    }

    @Override
    protected Component instantiate(InstantiationContext ctx) {
        throwOnInstantiate();
        return null;
    }

    @Override
    public void parseText(String text) {
        throwOnParseText();
    }

    private record VarmapStatement(boolean isAll, String src, String dst) {
        private static VarmapStatement individual(String src, String dst) {
            return new VarmapStatement(false, src, dst);
        }
        private static VarmapStatement all() {
            return new VarmapStatement(true, null, null);
        }
    }
}
