# ninedev
# Client
# Controller
# Service
# Repository
# Database

@Query(value = """
SELECT o.*
FROM orders o
LEFT JOIN users u
ON o.user_id = u.id
""", nativeQuery = true)
List<Order> findOrdersNative();

    // helper method (best practice)
    public void addOrder(Order order) {
        orders.add(order);
        order.setUser(this);
    }

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    @Transactional
    public Order createOrder(Long userId, Double price) {

        User user = userRepo.findById(userId)
                .orElseThrow();

        Order order = new Order();
        order.setCreatedAt(LocalDateTime.now());
        order.setTotalPrice(price);

        user.addOrder(order); // helper sync 2 chiều

        return orderRepo.save(order);
    }
}

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping("/{userId}")
    public Order create(
            @PathVariable Long userId,
            @RequestParam Double price) {

        return service.createOrder(userId, price);
    }
}

❌ Spring không auto link
❌ mappedBy không update DB
Owning Side bang so huu quan he