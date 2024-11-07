package Entities;

import java.util.Objects;

public class ClearedSection {
    private final int rowStart;
    private final int colStart;
    private final Player clearedBy;

    public ClearedSection( int rowStart, int colStart, Player clearedBy){
        this.rowStart = rowStart;
        this.colStart = colStart;
        this.clearedBy = clearedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClearedSection that = (ClearedSection) o;
        return rowStart == that.rowStart && colStart == that.colStart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowStart, colStart);
    }

    public int getRowStart() {
        return rowStart;
    }

    public int getColStart() {
        return colStart;
    }

    public Player getClearedBy() {
        return clearedBy;
    }
}
