package com.gregtechceu.gtceu.api.pipenet.amhs;

import java.util.LinkedHashSet;

/**
 * @author KilaBash
 * @date 2023/8/10
 * @implNote RailGraphEdge
 */
public record RailGraphEdge(AMHSRailNode from, AMHSRailNode to, LinkedHashSet<AMHSRailNode> path) {
}
