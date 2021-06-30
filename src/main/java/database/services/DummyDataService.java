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

    private Collection<Corporation> createDummyCorporations() throws SQLException {
        final var dummyCorporations = new ArrayList<Corporation>() {{
           add(new Corporation() {{ setName("VESCON GmbH"); }});
            add(new Corporation() {{ setName("Atos SE"); }});
            add(new Corporation() {{ setName("SAP SE"); }});
            add(new Corporation() {{ setName("MLP Finanzberatung SE"); }});
            add(new Corporation() {{ setName("Stark Industries"); }});
        }};

        corporationService.save(dummyCorporations);

        return dummyCorporations;
    }

    private void createDummyStudents(final Collection<Course> dummyCourses, final Collection<Corporation> dummyCorporations) throws SQLException {
        final var dummyStudents = new ArrayList<Student>() {{
           add(new Student() {{
               setMatriculationNumber("007");
               setFirstName("David");
               setLastName("Hedtke");
               setJavaSkillRating(JavaSkillRating.SEBASTIAN);
               setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("VESCON GmbH")).findFirst().get());
               setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
           }});

            add(new Student() {{
                setMatriculationNumber("42");
                setFirstName("Jan");
                setLastName("Baumann");
                setJavaSkillRating(JavaSkillRating.INTERMEDIATE);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("Atos SE")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber(null);
                setFirstName("Sebastian");
                setLastName("Damm");
                setJavaSkillRating(JavaSkillRating.SEBASTIAN);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().startsWith("MLP")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF19 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber(null);
                setFirstName("Eckard");
                setLastName("Kruse");
                setJavaSkillRating(JavaSkillRating.NONE);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("Atos SE")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI1")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("9000");
                setFirstName("Peter");
                setLastName("Parker");
                setJavaSkillRating(JavaSkillRating.ADVANCED);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("SAP SE")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF19 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("1");
                setFirstName("Tony");
                setLastName("Stark");
                setJavaSkillRating(JavaSkillRating.SEBASTIAN);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("Stark Industries")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI2")).findFirst().get());
            }});

            add(new Student() {{
                setMatriculationNumber("19");
                setFirstName("Mary-Jane");
                setLastName("Watson");
                setJavaSkillRating(JavaSkillRating.BEGINNER);
                setCorporation(dummyCorporations.stream().filter(x -> x.getName().equals("SAP SE")).findFirst().get());
                setCourse(dummyCourses.stream().filter(x -> x.getName().equals("TINF20 AI1")).findFirst().get());
            }});
        }};

        studentService.save(dummyStudents);
    }

    public void createDefaultDummyDataOnClearedDatabase() throws SQLException {

        studentService.clear();
        corporationService.clear();
        courseService.clear();
        roomService.clear();

        final var dummyRooms = createDummyRooms();
        final var dummyCourses = createDummyCourses(dummyRooms);
        final var dummyCorporations = createDummyCorporations();

        createDummyStudents(dummyCourses, dummyCorporations);
    }
}
