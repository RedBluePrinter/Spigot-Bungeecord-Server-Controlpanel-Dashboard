package tk.snapz.util;

public class TwoPartObject {
    public Object part1 = null;
    public Object part2 = null;
    public TwoPartObject(Object part1, Object part2) {
        this.part1 = part1;
        this.part2 = part2;
    }
    public class TwoPartString {
        public String part1 = null;
        public String part2 = null;
        TwoPartString(String part1, String part2) {
            this.part1 = part1;
            this.part2 = part2;
        }
    }
}