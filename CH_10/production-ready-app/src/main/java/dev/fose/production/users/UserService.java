package dev.fose.production.users;

import dev.fose.production.config.FeatureFlags;
import dev.fose.production.config.ServiceUnavailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final FeatureFlags featureFlags;

    // In-memory storage for demo (would be a database in production)
    private final ConcurrentHashMap<Long, User> users = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UserService(PasswordEncoder passwordEncoder,
                       EmailService emailService,
                       FeatureFlags featureFlags) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.featureFlags = featureFlags;
    }

    public User createUser(User user) {
        logger.info("Creating new user with username: {}", user.getUsername());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        Long id = idGenerator.getAndIncrement();
        user.setId(id);
        users.put(id, user);

        logger.debug("User created successfully with id: {}", id);

        if (featureFlags.isEmailNotifications()) {
            try {
                emailService.sendWelcomeEmail(user);
                logger.info("Welcome email sent to user: {}", user.getUsername());
            } catch (ServiceUnavailableException e) {
                logger.warn("Failed to send welcome email to user {}: {}",
                    user.getUsername(), e.getMessage());
            }
        }

        return user;
    }

    public User findById(Long id) {
        logger.debug("Looking up user with id: {}", id);

        User user = users.get(id);
        if (user == null) {
            logger.error("User not found with id: {}", id);
            throw new UserNotFoundException(id);
        }

        return user;
    }

    public List<User> findAll() {
        logger.debug("Retrieving all users, count: {}", users.size());
        return new ArrayList<>(users.values());
    }

    public void deleteUser(Long id) {
        logger.info("Deleting user with id: {}", id);

        User removedUser = users.remove(id);
        if (removedUser == null) {
            logger.error("Cannot delete - user not found with id: {}", id);
            throw new UserNotFoundException(id);
        }

        logger.info("User deleted successfully: {}", removedUser.getUsername());
    }
}
