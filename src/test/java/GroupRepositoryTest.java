class GroupRepositoryTest {
//    GroupRepository groupRepository = new GroupRepository();
//    Group group = new Group("TestGroup", null);
//
//    @BeforeEach
//    public void clearDB() {
//        groupRepository.clear();
//    }
//
//    @Test
//    @DisplayName("CreateGroup")
//    void create() {
//        groupRepository.create(group);
//        assertEquals(group, groupRepository.findByName(group.getName()).get());
//    }
//
//    @Test
//    @DisplayName("UpdateGroup")
//    void update() {
//        groupRepository.create(group);
//        Group group1 = new Group("UpdatedGroup", null);
//        groupRepository.update(group1, group.getId());
//        group1.setId(group.getId());
//        assertEquals(group1, groupRepository.findByName("UpdatedGroup").get());
//    }
//
//    @Test
//    @DisplayName("DeleteGroup")
//    void delete() {
//        groupRepository.create(group);
//        assertTrue(groupRepository.findById(group.getId()).isPresent());
//        groupRepository.delete(group.getId());
//        assertFalse(groupRepository.findById(group.getId()).isPresent());
//    }
//
//    @Test
//    @DisplayName("FindById")
//    void findById() {
//        groupRepository.create(group);
//        assertTrue(groupRepository.findById(group.getId()).isPresent());
//    }
//
//    @Test
//    @DisplayName("FindAll")
//    void findAll() {
//        groupRepository.create(group);
//        assertTrue(!groupRepository.findAll().isEmpty());
//    }
//
//    @Test
//    @DisplayName("FindAllRoot")
//    void findAllRoot() {
//        groupRepository.create(group);
//        assertEquals(groupRepository.findAllRoot(), groupRepository.findAll().stream()
//                .filter(group1 -> group1.getParent() == null)
//                .collect(Collectors.toList()));
//    }
//
//    @Test
//    @DisplayName("FindByName")
//    void findByName() {
//        groupRepository.create(group);
//        assertTrue(groupRepository.findByName(group.getName()).isPresent());
//    }
//
//    @Test
//    @DisplayName("FindByParent")
//    void findByParent() {
//
//        Group group1 = new Group("Sub", group);
//        List<Group> list = new ArrayList<>();
//        list.add(group1);
//        groupRepository.create(group);
//        groupRepository.create(group1);
//        assertEquals(list, groupRepository.findByParent(group));
//    }

}