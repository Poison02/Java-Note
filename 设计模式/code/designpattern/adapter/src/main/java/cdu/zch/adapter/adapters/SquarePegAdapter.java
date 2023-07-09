package cdu.zch.adapter.adapters;

import cdu.zch.adapter.round.RoundPeg;
import cdu.zch.adapter.square.SquarePeg;

/**
 * @author Zch
 * @date 2023/7/9
 **/
public class SquarePegAdapter extends RoundPeg {

    private SquarePeg peg;

    public SquarePegAdapter(SquarePeg peg) {
        this.peg = peg;
    }

    @Override
    public double getRadius() {
        double result;
        // Calculate a minimum circle radius, which can fit this peg.
        result = (Math.sqrt(Math.pow((peg.getWidth() / 2), 2) * 2));
        return result;
    }

}
