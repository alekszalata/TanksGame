package Level;

/**
 * Created by aleks on 27.06.2017.
 */
    public enum TileType {
        EMPTY(0) , BRICK(1) , BUSH(2);

        private int n;

        TileType(int n) {
            this.n = n;
        }

        public static TileType fromNumeric(int n) {
            switch (n) {
                case 1:
                    return BRICK;
                case 2:
                    return BUSH;
                default:
                    return EMPTY;
            }
        }
}
