package database.models;

import java.util.Collection;
import java.util.Objects;

public class Corporation implements Model {

    private Integer id;
    private String name;

    public Corporation() {
    }

    public Corporation(final Integer id, final String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Corporation that = (Corporation) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Corporation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
