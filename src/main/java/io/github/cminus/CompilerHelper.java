package io.github.cminus;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.antlr.v4.runtime.tree.Tree;

import java.util.Arrays;
import java.util.List;

import static io.github.cminus.CompilerUtils.*;

public class CompilerHelper {
    public static String getSyntaxTree(Parser parser, ParseTree root) {
        StringBuilder buf = new StringBuilder();
        buildSyntaxTree(root, buf, 0, Arrays.asList(parser.getRuleNames()));
        return buf.toString();
    }

    private static void buildSyntaxTree(ParseTree root, StringBuilder buffer, int offset, List<String> ruleNames) {
        if (root instanceof ParserRuleContext) {
            ParserRuleContext prc = (ParserRuleContext) root;
            if (prc.children != null && prc.children.size() > 0 && !"<EOF>".equals(prc.children.get(0).getText())) {
                // Build content
                String text = getNodeText(prc, ruleNames);
                Integer line = getLine(prc);
                buffer
                        .append("  ".repeat(offset))
                        .append(text)
                        .append("(")
                        .append(line)
                        .append(")")
                        .append("\n");
                // Recursive
                for (ParseTree child : prc.children) {
                    buildSyntaxTree(child, buffer, offset + 1, ruleNames);
                }
            }
        } else {
            String result = getNodeText(root, ruleNames);
            buffer
                    .append("  ".repeat(offset))
                    .append(result)
                    .append("\n");
        }
    }

    private static String getNodeText(Tree node, List<String> ruleNames) {
        if (node instanceof RuleContext) {
            int ruleIndex = ((RuleContext)node).getRuleContext().getRuleIndex();
            String ruleName = ruleNames.get(ruleIndex);
            return upperFirstChar(ruleName);
        }

        if (node instanceof TerminalNode) {
            Token symbol = ((TerminalNode)node).getSymbol();
            if (symbol != null) {
                int tokenType= symbol.getType();
                switch (tokenType) {
                    case CMinusLexer.ID:    return "ID: " + symbol.getText();
                    case CMinusLexer.TYPE:  return "TYPE: " + symbol.getText();
                    case CMinusLexer.INT:   return "INT: " + strToInt(symbol.getText());
                    case CMinusLexer.FLOAT: return "FLOAT: " + strToFloat(symbol.getText());
                    default: return CMinusLexer.VOCABULARY.getSymbolicName(tokenType);
                }
            }
        }

        if (node instanceof ErrorNode) {
            return node.toString();
        }

        return null;
    }

    private static Integer getLine(ParseTree root) {
        if (root instanceof TerminalNode) {
            TerminalNode terminalNode = (TerminalNode)root;
            return terminalNode.getSymbol().getLine();
        }
        ParserRuleContext context = (ParserRuleContext)root;
        return getLine(context.getChild(0));
    }
}
