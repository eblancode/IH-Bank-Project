package main.controllers.users;

/*@RestController
@RequestMapping("/user")*/
public class _UserController {
    /*@Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;*/

    // ADMIN
    /*@GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody User user) {
        System.err.println();
        if(userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        }
        return null;
    }

    @PatchMapping("/user-name/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateStatus(@PathVariable Long id, @RequestParam String userName){
        if(userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            user.setUserName(userName);
            return userRepository.save(user);
        }
        return null;
    }*/

}
