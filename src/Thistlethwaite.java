import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Thistlethwaite {

    HashMap<Character, Color> colorToFace;
    Cube cube;


    public Thistlethwaite() {
    }

    public Cube solve(Cube cube) {
        this.cube = cube;
        colorToFace = this.pairSides();
        this.phaseOne();

        return cube;
    }


    public HashMap<Character, Color> pairSides() {
        HashMap<Character, Color> encoder = new HashMap<>();
        encoder.put('F', Color.GREEN);
        encoder.put('U', Color.WHITE);
        encoder.put('R', Color.RED);
        encoder.put('L', Color.ORANGE);
        encoder.put('D', Color.YELLOW);
        encoder.put('B', Color.BLUE);
        return encoder;
    }

    /**
     * Orients cube so that no U or D quarter turns are required, moving us into group 1
     */
    private void phaseOne() {
        Side frontFace = this.cube.getSide(this.colorToFace.get('F'));
        Side backFace = this.cube.getSide(this.colorToFace.get('B'));
        Side rightFace = this.cube.getSide(this.colorToFace.get('R'));
        Side leftFace = this.cube.getSide(this.colorToFace.get('L'));
        Side upFace = this.cube.getSide(this.colorToFace.get('U'));
        Side downFace = this.cube.getSide(this.colorToFace.get('D'));

        int countBad = 1;
        ArrayList<Edge> badEdges = new ArrayList();
        while (countBad > 0) {
            countBad = 0;
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    if ((x == 1 && y == 0) || (x == 1 && y == 2) || (x == 0 && y == 1) || (x == 2 && y == 1)) {
                        Character edgeF = frontFace.squares[x][y];
                        if (edgeF.equals(this.colorToFace.get('L').getLetter())
                                || edgeF.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('F', x, y));
                        } else if (edgeF.equals(this.colorToFace.get('U').getLetter())
                                || edgeF.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeF = null;
                            if (y == 1) {
                                if (x == 0) {oppEdgeF = leftFace.getSquares()[2][1];}
                                if (x == 2) {oppEdgeF = rightFace.getSquares()[0][1];}
                            }
                            else if (x == 1) {
                                if (y == 0) {oppEdgeF = downFace.getSquares()[1][2];}
                                if (y == 2) {oppEdgeF = upFace.getSquares()[1][0];}
                            }
                            if (oppEdgeF.equals(this.colorToFace.get('F').getLetter()) ||
                                oppEdgeF.equals(this.colorToFace.get('B').getLetter())) {
                                countBad = countBad + 1;
                                badEdges.add(Edge.edgeNumber('F', x, y));
                            }
                        }

                        Character edgeB = backFace.squares[x][y];
                        if (edgeB.equals(this.colorToFace.get('L').getLetter()) ||
                                edgeB.equals(this.colorToFace.get('R').getLetter())) {
                            countBad++;
                            badEdges.add(Edge.edgeNumber('B', x, y));
                        } else if (edgeB.equals(this.colorToFace.get('U').getLetter())
                                || edgeB.equals(this.colorToFace.get('D').getLetter())) {
                            Character oppEdgeB = null;
                            if (y == 1) {
                                if (x == 0) {oppEdgeB = rightFace.getSquares()[2][1];}
                                if (x == 2) {oppEdgeB = leftFace.getSquares()[0][1];}
                            }
                            else if (x == 1) {
                                if (y == 0) {oppEdgeB = downFace.getSquares()[1][0];}
                                if (y == 2) {oppEdgeB = upFace.getSquares()[1][2];}
                            }
                            if (oppEdgeB.equals(this.colorToFace.get('F').getLetter()) ||
                                    oppEdgeB.equals(this.colorToFace.get('B').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('B', x, y));
                            }
                        }
                        if ((x == 0 && y == 1) || (x == 2 && y == 1)) {
                            Character edgeU = upFace.squares[x][y];
                            if (edgeU.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeU.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('U', x, y));
                            } else if (edgeU.equals(this.colorToFace.get('U').getLetter())
                                    || edgeU.equals(this.colorToFace.get('D').getLetter()))  {
                                Character oppEdgeU = null;
                                if (x == 0) {oppEdgeU = leftFace.getSquares()[1][2];}
                                if (x == 2) {oppEdgeU = rightFace.getSquares()[1][2];}
                                if (oppEdgeU.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeU.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('U', x, y));
                                }
                            }

                            Character edgeD = downFace.squares[x][y];
                            if (edgeD.equals(this.colorToFace.get('L').getLetter()) ||
                                    edgeD.equals(this.colorToFace.get('R').getLetter())) {
                                countBad++;
                                badEdges.add(Edge.edgeNumber('D', x, y));
                            } else if (edgeD.equals(this.colorToFace.get('U').getLetter())
                                    || edgeD.equals(this.colorToFace.get('D').getLetter()))  {
                                Character oppEdgeD = null;
                                if (x == 0) {oppEdgeD = leftFace.getSquares()[1][0];}
                                if (x == 2) {oppEdgeD = rightFace.getSquares()[1][0];}
                                if (oppEdgeD.equals(this.colorToFace.get('F').getLetter()) ||
                                        oppEdgeD.equals(this.colorToFace.get('B').getLetter())) {
                                    countBad++;
                                    badEdges.add(Edge.edgeNumber('D', x, y));
                                }
                            }
                        }
                    }
                }
            }

            //Now that we have our edges, we have to act
            int uCount = 0;
            int dCount= 0;
            int midCount = 0;
            ArrayList<Edge> upEdges = new ArrayList<>();
            ArrayList<Edge> downEdges = new ArrayList<>();
            ArrayList<Edge> midEdges = new ArrayList<>();
            for (Edge e : badEdges) {
                if (e.secondary.equals('R') || e.secondary.equals('L')) {
                    midCount++;
                    midEdges.add(e);
                } else if (e.secondary.equals('U')){
                    uCount++;
                    upEdges.add(e);
                } else if (e.secondary.equals('D')) {
                    dCount++;
                    downEdges.add(e);
                }
            }
            ArrayList<Move> moves = new ArrayList<Move>();
            HashMap<Character, Character> translate = null;
            switch(badEdges.size()) {
                case 0:
                    //By some miracle(.05% chance) no edges are bad! Do nothing.
                    countBad = 0;
                    break;
                case 2:
                    if (uCount == 2) {
                        moves.add(Move.uRandom);
                    }
                    if (dCount == 2) {
                        moves.add(Move.dRandom);
                    }
                    if (uCount == 1) {
                        if (dCount == 1) {
                            moves.add(Move.uRandom);
                            moves.add(Move.dRandom);
                        } else if (midCount == 1) {
                            Edge m = midEdges.get(0);
                            Edge u = upEdges.get(0);
                            switch (m) {
                                case FL:
                                    if (u.equals(Edge.FU)) {
                                        
                                    }
                            }
                        }
                    }




                case 4:
                    if (uCount == 4) {
                        this.cube.rotate(this.colorToFace.get('U'), true);
                    } else if (dCount == 4) {
                        this.cube.rotate(this.colorToFace.get('D'), true);
                    } else if (midCount == 4) {
                        this.cube.rotate(this.colorToFace.get('L'), true);
                        this.cube.rotate(this.colorToFace.get('R'), true);
                        this.cube.rotate(this.colorToFace.get('U'), true);
                        this.cube.rotate(this.colorToFace.get('D'), true);
                    } else if (uCount == 2 && dCount == 2) {
                        this.cube.rotate(this.colorToFace.get('U'), true);
                        this.cube.rotate(this.colorToFace.get('D'), true);
                    } else if (midCount == 2 && uCount == 2) {
                        Edge upOne = upEdges.get(0);
                        Edge upTwo = upEdges.get(1);
                        boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
                        boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
                        Character sharedFace;
                        if (samePrim || sameSecon) {
                            if (samePrim) {
                                sharedFace = midEdges.get(0).primary;
                            } else {
                                sharedFace = midEdges.get(0).secondary;
                            }
                            translate = this.horizontalMap(sharedFace);
                            if (this.onSide(sharedFace, upOne, upTwo)) {
                                if (this.onSide(translate.get('L'), upOne, upTwo)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.lClock);
                                    moves.add(Move.dCounter);
                                    moves.add(Move.bEighty);
                                    moves.add(Move.lCounter);
                                } else if (this.onSide(translate.get('R'), upOne, upTwo)) {
                                    moves.add(Move.lCounter);
                                    moves.add(Move.rCounter);
                                    moves.add(Move.dClock);
                                    moves.add(Move.bEighty);
                                    moves.add(Move.rClock);
                                } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.lCounter);
                                }
                            } else if (this.onSide(translate.get('L'), upOne, upTwo)) {
                                if (this.onSide(translate.get('R'), upOne, upTwo)) {
                                    moves.add(Move.fClock);
                                    moves.add(Move.dEighty);
                                    moves.add(Move.bEighty);
                                } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.fClock);
                                }
                            } else if (this.onSide(translate.get('B'), upOne, upTwo)) {
                                if (this.onSide(translate.get('R'), upOne, upTwo)) {
                                    moves.add(Move.lCounter);
                                    moves.add(Move.fClock);
                                }
                            }
                        } else {
                            Character base = null;
                            if (this.onSide(midEdges.get(0).primary, upOne, upTwo)) {
                                base = midEdges.get(0).primary;
                            } else if (this.onSide(midEdges.get(0).secondary, upOne, upTwo)) {
                                base = midEdges.get(0).secondary;
                            } else if (this.onSide(midEdges.get(1).primary, upOne, upTwo)) {
                                base = midEdges.get(1).primary;
                            } else if (this.onSide(midEdges.get(1).secondary, upOne, upTwo)) {
                                base = midEdges.get(1).secondary;
                            }
                            translate = this.horizontalMap(base);
                            if (this.onSide(translate.get('B'), upOne, upTwo)) {
                                moves.add(Move.rClock);
                                moves.add(Move.lClock);
                            } else if (this.onSide(translate.get('L'), upOne, upTwo)) {
                                moves.add(Move.rClock);
                                moves.add(Move.bCounter);
                            } else if (this.onSide(translate.get('R'), upOne, upTwo)) {
                                moves.add(Move.lClock);
                                moves.add(Move.uClock);
                                moves.add(Move.rClock);
                            }
                        }
                    } else if (midCount == 2 && dCount == 2) {
                        Edge downOne = downEdges.get(0);
                        Edge downTwo = downEdges.get(1);
                        boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
                        boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
                        Character sharedFace;
                        if (samePrim || sameSecon) {
                            if (samePrim) {
                                sharedFace = midEdges.get(0).primary;
                            } else {
                                sharedFace = midEdges.get(0).secondary;
                            }
                            translate = this.horizontalMap(sharedFace);
                            if (this.onSide(sharedFace, downOne, downTwo)) {
                                if (this.onSide(translate.get('L'), downOne, downTwo)) {
                                    moves.add(Move.rCounter);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.dClock);
                                    moves.add(Move.bEighty);
                                    moves.add(Move.lClock);
                                } else if (this.onSide(translate.get('R'), downOne, downTwo)) {
                                    moves.add(Move.lClock);
                                    moves.add(Move.rClock);
                                    moves.add(Move.dCounter);
                                    moves.add(Move.bEighty);
                                    moves.add(Move.rCounter);
                                } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                                    moves.add(Move.rCounter);
                                    moves.add(Move.lClock);
                                }
                            } else if (this.onSide(translate.get('L'), downOne, downTwo)) {
                                if (this.onSide(translate.get('R'), downOne, downTwo)) {
                                    moves.add(Move.fCounter);
                                    moves.add(Move.dEighty);
                                    moves.add(Move.bEighty);
                                } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                                    moves.add(Move.rCounter);
                                    moves.add(Move.fCounter);
                                }
                            } else if (this.onSide(translate.get('B'), downOne, downTwo)) {
                                if (this.onSide(translate.get('R'), downOne, downTwo)) {
                                    moves.add(Move.lClock);
                                    moves.add(Move.fCounter);
                                }
                            }
                        } else {
                            Character base = null;
                            if (this.onSide(midEdges.get(0).primary, downOne, downTwo)) {
                                base = midEdges.get(0).primary;
                            } else if (this.onSide(midEdges.get(0).secondary, downOne, downTwo)) {
                                base = midEdges.get(0).secondary;
                            } else if (this.onSide(midEdges.get(1).primary, downOne, downTwo)) {
                                base = midEdges.get(1).primary;
                            } else if (this.onSide(midEdges.get(1).secondary, downOne, downTwo)) {
                                base = midEdges.get(1).secondary;
                            }
                            translate = this.horizontalMap(base);
                            if (this.onSide(translate.get('B'), downOne, downTwo)) {
                                moves.add(Move.rCounter);
                                moves.add(Move.lCounter);
                            } else if (this.onSide(translate.get('L'), downOne, downTwo)) {
                                moves.add(Move.rCounter);
                                moves.add(Move.bClock);
                            } else if (this.onSide(translate.get('R'), downOne, downTwo)) {
                                moves.add(Move.lCounter);
                                moves.add(Move.uCounter);
                                moves.add(Move.rCounter);
                            }
                        }
                    } else if (midCount == 2 && uCount == 1 && dCount == 1) {
                        Edge upOne = upEdges.get(0);
                        Edge downOne = downEdges.get(0);
                        boolean samePrim = midEdges.get(0).primary == midEdges.get(1).primary;
                        boolean sameSecon = midEdges.get(0).secondary == midEdges.get(1).secondary;
                        Character sharedFace;
                        if (samePrim || sameSecon) {
                            if (samePrim) {
                                sharedFace = midEdges.get(0).primary;
                            } else {
                                sharedFace = midEdges.get(0).secondary;
                            }
                            translate = this.horizontalMap(sharedFace);
                            if (sharedFace.equals(upOne.primary) || sharedFace.equals(upOne.secondary)) {
                                if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.dEighty);
                                    moves.add(Move.bEighty);
                                } else if (translate.get('R').equals(downOne.primary) || translate.get('R').equals(downOne.secondary)) {
                                    moves.add(Move.dClock);
                                    moves.add(Move.rClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.bEighty);
                                } else if (translate.get('L').equals(downOne.primary) || translate.get('L').equals(downOne.secondary)) {
                                    moves.add(Move.dCounter);
                                    moves.add(Move.rClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.bEighty);
                                } else if (translate.get('B').equals(downOne.primary) || translate.get('B').equals(downOne.secondary)) {
                                    moves.add(Move.rClock);
                                    moves.add(Move.lCounter);
                                    moves.add(Move.bEighty);
                                }
                            }
                            if (sharedFace.equals(downOne.primary) || sharedFace.equals(downOne.secondary)) {
                                if (translate.get('R').equals(upOne.primary) || translate.get('R').equals(upOne.secondary)) {
                                    moves.add(Move.uCounter);
                                    moves.add(Move.rCounter);
                                    moves.add(Move.lClock);
                                    moves.add(Move.bEighty);
                                } else if (translate.get('L').equals(upOne.primary) || translate.get('L').equals(upOne.secondary)) {
                                    moves.add(Move.uClock);
                                    moves.add(Move.rCounter);
                                    moves.add(Move.lClock);
                                    moves.add(Move.bEighty);
                                } else if (translate.get('B').equals(upOne.primary) || translate.get('B').equals(upOne.secondary)) {
                                    moves.add(Move.rCounter);
                                    moves.add(Move.lClock);
                                    moves.add(Move.bEighty);
                                }
                            }
                        } else {
                            this.cube.oneEighty(Color.GREEN);
                        }
                    } else if (midCount == 1 && dCount == 1 & uCount == 2) {
                        Edge mid = midEdges.get(0);
                        Edge upOne = upEdges.get(0);
                        Edge upTwo = upEdges.get(1);
                        Edge downOne = downEdges.get(0);
                        while (this.onSide(downOne.primary, upOne, upTwo) || this.onSide(downOne.secondary, upOne, upTwo)) {
                            this.cube.rotate(Color.WHITE, true);
                        }
                        Character downturn;
                        if (downOne.primary.equals('D')) {
                            downturn = downOne.secondary;
                        } else {
                            downturn = downOne.primary;
                        }
                        this.cube.oneEighty(this.colorToFace.get(downturn));
                        while (this.oneOnSide(mid.primary, upOne) || this.oneOnSide(mid.primary, upTwo) || this.oneOnSide(mid.primary, downOne)) {
                            this.cube.rotate(Color.WHITE, true);
                        }
                        moves.add(Move.getMove(mid.primary, Direction.Clockwise));
                    } else if (midCount == 1 && dCount == 2 & uCount == 1) {
                        Edge mid = midEdges.get(0);
                        Edge downOne = downEdges.get(0);
                        Edge downTwo = downEdges.get(1);
                        Edge upOne = upEdges.get(0);
                        while (this.onSide(downOne.primary, downOne, downTwo) || this.onSide(downOne.secondary, downOne, downTwo)) {
                            this.cube.rotate(Color.YELLOW, true);
                        }
                        Character upturn;
                        if (upOne.primary.equals('U')) {
                            upturn = upOne.secondary;
                        } else {
                            upturn = upOne.primary;
                        }
                        this.cube.oneEighty(this.colorToFace.get(upturn));
                        while (this.oneOnSide(mid.primary, downOne) || this.oneOnSide(mid.primary, downTwo) || this.oneOnSide(mid.primary, upOne)) {
                            this.cube.rotate(Color.YELLOW, true);
                        }
                        moves.add(Move.getMove(mid.primary, Direction.Counterclockwise));
                    } else if (dCount == 1 & uCount == 3) {
                        Edge upOne = upEdges.get(0);
                        Edge upTwo = upEdges.get(1);
                        Edge upThree = upEdges.get(2);
                        Edge downOne = downEdges.get(0);
                        while (this.onSide(downOne.primary, upOne, upTwo) || this.onSide(downOne.secondary, upOne, upTwo) || this.oneOnSide(downOne.primary, upThree) || this.oneOnSide(downOne.secondary, upThree)) {
                            this.cube.rotate(Color.WHITE, true);
                        }
                        Character downturn;
                        if (downOne.primary.equals('D')) {
                            downturn = downOne.secondary;
                        } else {
                            downturn = downOne.primary;
                        }
                        this.cube.oneEighty(this.colorToFace.get(downturn));
                    } else if (dCount == 3 & uCount == 1) {
                        Edge downOne = downEdges.get(0);
                        Edge downTwo = downEdges.get(1);
                        Edge downThree = downEdges.get(2);
                        Edge upOne = upEdges.get(0);
                        while (this.onSide(upOne.primary, downOne, downTwo) || this.onSide(upOne.secondary, downOne, downTwo) || this.oneOnSide(upOne.primary, downThree) || this.oneOnSide(upOne.secondary, downThree)) {
                            this.cube.rotate(Color.YELLOW, true);
                        }
                        Character upturn;
                        if (upOne.primary.equals('U')) {
                            upturn = upOne.secondary;
                        } else {
                            upturn = upOne.primary;
                        }
                        this.cube.oneEighty(this.colorToFace.get(upturn));
                    }
                case 6:


                default:
                    throw new IllegalStateException("Unexpected value: " + badEdges.size());
            }
            this.translateAndExecute(translate, moves);
        }
        System.out.println(countBad);
    }

    private HashMap<Character,Character> horizontalMap(Character c) {
        HashMap<Character, Character> result = new HashMap<Character, Character>();
        switch(c) {
            case 'F':
                result.put('F', 'F');
                result.put('R', 'R');
                result.put('B', 'B');
                result.put('L', 'L');
            case 'L':
                result.put('F', 'L');
                result.put('R', 'F');
                result.put('B', 'R');
                result.put('L', 'B');
            case 'B':
                result.put('F', 'B');
                result.put('R', 'L');
                result.put('B', 'F');
                result.put('L', 'R');
            case 'R':
                result.put('F', 'R');
                result.put('R', 'B');
                result.put('B', 'L');
                result.put('L', 'F');
        }
        return result;
    }

    private boolean onSide(Character c, Edge one, Edge two) {
        return c.equals(one.primary) || c.equals(one.secondary) || c.equals(two.primary) || c.equals(two.secondary);
    }

    private boolean oneOnSide(Character c,Edge e) {
        return c.equals(e.primary) || c.equals(e.secondary);
    }

    private boolean shareFace(Edge one, Edge  two) {
        return one.primary.equals(two.primary) || one.primary.equals(two.secondary) || one.secondary.equals(two.primary) || one.secondary.equals(two.secondary);
    }

    private void translateAndExecute(HashMap<Character, Character> map, List<Move> moves) {

    }



}
