package database.services;

import database.models.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

public class DummyDataService {
    private static DummyDataService service;

    private ModelService<Course> courseService;
    private ModelService<Room> roomService;
    private ModelService<Student> studentService;

    private DummyDataService(){
        courseService = CourseService.getService();
        roomService = RoomService.getService();
        studentService = StudentService.getService();
    }

    public static DummyDataService getService() {
        if (service == null)
            service = new DummyDataService();

        return service;
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

    private void createDummyStudents(final Collection<Course> dummyCourses) throws SQLException {
        final var dummyStudents = new ArrayList<Student>() {{
           add(new Student() {{
               setMatriculationNumber("007");
               setFirstName("David");
               setLastName("Hedtke");
               setJavaSkillRating(JavaSkillRating.ADVANCED);
               setCorporationName("VESCON GmbH");
               setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
           }});

            add(new Student() {{
                setMatriculationNumber("42");
                setFirstName("Jan");
                setLastName("Baumann");
                setJavaSkillRating(JavaSkillRating.INTERMEDIATE);
                setCorporationName("Atos SE");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("10");
                setFirstName("Sebastian");
                setLastName("Damm");
                setJavaSkillRating(JavaSkillRating.SEBASTIAN);
                setCorporationName("MLP Finanzberatung SE");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF19 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("00000");
                setFirstName("Eckard");
                setLastName("Kruse");
                setJavaSkillRating(JavaSkillRating.NONE);
                setCorporationName("Atos SE");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI1")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("9000");
                setFirstName("Peter");
                setLastName("Parker");
                setJavaSkillRating(JavaSkillRating.ADVANCED);
                setCorporationName("SAP SE");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF19 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("1");
                setFirstName("Tony");
                setLastName("Stark");
                setJavaSkillRating(JavaSkillRating.SEBASTIAN);
                setCorporationName("Stark Industries");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("19");
                setFirstName("Mary-Jane");
                setLastName("Watson");
                setJavaSkillRating(JavaSkillRating.BEGINNER);
                setCorporationName("SAP SE");
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI1")).findFirst().get());
            }});
        }};

        studentService.save(dummyStudents);
    }

    public void createDefaultDummyDataIfDatabaseIsEmpty() throws SQLException {

        if (!roomService.isEmpty() || !courseService.isEmpty() || !studentService.isEmpty())
            return;

        System.out.println("Creating dummy data...");

        final var dummyRooms = createDummyRooms();
        final var dummyCourses = createDummyCourses(dummyRooms);

        createDummyStudents(dummyCourses);

        System.out.println("Dummy data created.");
    }
}
