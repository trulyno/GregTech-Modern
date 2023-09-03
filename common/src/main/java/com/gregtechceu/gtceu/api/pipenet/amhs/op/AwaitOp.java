package com.gregtechceu.gtceu.api.pipenet.amhs.op;

import com.gregtechceu.gtceu.api.entity.FOUPCartEntity;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailNet;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailNode;
import com.gregtechceu.gtceu.api.pipenet.amhs.AMHSRailType;

/**
 * @author KilaBash
 * @date 2023/8/13
 * @implNote AwaitOp
 */
public class AwaitOp extends MoveOp {

    public AwaitOp(FOUPCartEntity cart) {
        super(cart);
    }

    @Override
    public OP getType() {
        return OP.AWAIT;
    }

    @Override
    protected boolean updateCart(AMHSRailNet net) {
        if (cart.isAwaited()) {
            return true;
        }
        var node = net.getNodeAt(cart.getOnPos());
        if (destination == null) {
            var nodes = net.getFreeFoupRails();
            for (AMHSRailNode dest : nodes) {
                var p = net.routePath(node, dest);
                if (path == null || p.size() < path.size()) {
                    if (p.isEmpty()) continue;
                    setPath(p);
                    this.destination = dest.pos;
                    this.cart.setAwaitedPos(destination);
                }
            }
        }
        if (destination != null) {
            if (super.updateCart(net)) {
                var dest = net.getNodeAt(destination);
                if (dest != null && dest.railType == AMHSRailType.FOUP) {
                    if (!cart.isAwaited()) {
                        cart.setAwaitingData(cart.getAwaitingData() + 1);
                    }
                } else {
                    cart.setAwaitingData(1);
                    cart.setAwaitedPos(null);
                    destination = null;
                }
            }
        }
        return false;
    }
}
