package database.services;

import database.models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DummyDataService {
    private static DummyDataService service;

    private ModelService<Corporation> corporationService;
    private ModelService<Course> courseService;
    private ModelService<Room> roomService;
    private ModelService<Student> studentService;

    private DummyDataService(){
        corporationService = CorporationService.getService();
        courseService = CourseService.getService();
        roomService = RoomService.getService();
        studentService = StudentService.getService();
    }

    private Collection<Course> createDummyCourses(final Collection<Room> dummyRooms) throws SQLException {
        final var dummyCourses = new ArrayList<Course>() {{
            add(new Course() {{
                setName("TINF20 AI1");
                setRoom(dummyRooms.stream().filter(x -> x.getName().equals("SAP")).findFirst().get());
            }});
            add(new Course() {{
                setName("TINF20 AI2");
                setRoom(dummyRooms.stream().filter(x -> x.getName().equals("Raum 404")).findFirst().get());
            }});
            add(new Course() {{
                setName("TINF19 AI2");
                setRoom(dummyRooms.stream().filter(x -> x.getName().equals("Raum 505")).findFirst().get());
            }});
        }};

        courseService.save(dummyCourses);

        return dummyCourses;
    }

    private Collection<Room> createDummyRooms() throws SQLException {
        final var dummyRooms = new ArrayList<Room>() {{
            add(new Room() {{ setName("SAP"); }});
            add(new Room() {{ setName("Raum 101"); }});
            add(new Room() {{ setName("Raum 202"); }});
            add(new Room() {{ setName("Raum 303"); }});
            add(new Room() {{ setName("Raum 404"); }});
            add(new Room() {{ setName("Raum 505"); }});
        }};

        roomService.save(dummyRooms);
        return dummyRooms;
    }

    public static DummyDataService getService() {
        if (service == null)
            service = new DummyDataService();

        return service;
    }

    public void createDefaultDummyDataOnClearedDatabase() throws SQLException {

        studentService.clear();
        corporationService.clear();
        courseService.clear();
        roomService.clear();

        final var dummyRooms = createDummyRooms();

        final var dummyCourses = createDummyCourses(dummyRooms);
    }
}
