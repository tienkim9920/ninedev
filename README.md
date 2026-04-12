# ninedev

# Client

# Controller

# Service

# Repository

# Database


@Getter
@Setter
@NoArgsConstructor
public class OrderRequestDto {
    private String createdAt;

    @NotNull(message = "Gia san pham khong duoc de trong")
    private double totalPrice;

    @NotNull(message = "user_id khong duoc de trong")
    private Long userId;
}

// helper method (best practice)
public void addOrder(OrderEntity order) {
    orders.add(order);
    order.setUser(this);
}

@Service
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepo;
    private final OrderRepository orderRepo;

    @Transactional
    public boolean createOrder(OrderRequestDto orderRequestDto) {
        UserEntity user = userRepository.findById(orderRequestDto.getUser_id()).orElseThrow();

        OrderEntity order = new OrderEntity();
        order.setTotal_price(orderRequestDto.getTotalPrice());

        user.addOrder(order);
        orderRepository.save(order);

        return true;
    }

}

@PostMapping("/orders")
public ResponseEntity<ResponseDTO> createOrder(@Valid @RequestBody OrderRequestDto orderRequestDto) {
    boolean isCreate = orderService.createOrder(orderRequestDto);
    return ResponseEntity.ok(
            new ResponseDTO(200, true, "Tao don hang thanh cong", isCreate)
    );
}

❌ Spring không auto link
❌ mappedBy không update DB
Owning Side bang so huu quan he
