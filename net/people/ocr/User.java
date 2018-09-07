package net.people.ocr;

public class User {

    public String name;
    public String age;
    private ClassRoom room;

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return name + "->" + age + ":" + room;
    }

}
