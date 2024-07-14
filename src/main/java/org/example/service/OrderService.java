package org.example.service;

import org.example.dto.payment.request.OrderCreateRequest;
import org.example.dto.payment.response.option.OptionDetailDTO;
import org.example.dto.payment.response.order.OrderPreviewDTO;
import org.example.dto.payment.response.order.OrderPreviewsResponse;
import org.example.dto.payment.response.order.OrderResponse;
import org.example.dto.payment.response.place.PlacePreviewDTO;
import org.example.dto.payment.response.place.PlacePreviewDTOOrder;
import org.example.dto.payment.response.tariff.TariffDTO;
import org.example.dto.payment.response.user.UserInfoDTO;
import org.example.model.*;
import org.example.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderOptionRepository orderOptionRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TariffRepository tariffRepository;
    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    OptionRepository optionRepository;

    public OrderResponse createOrder(Long userId, OrderCreateRequest body) {
        System.out.println(body);
        if (body.getPlaceId() == null || body.getTariffId() == null || body.getDaysCount() == null || body.getDaysCount() < 1) {
            throw new IllegalArgumentException();
        }
        Order order = new Order();
        User user = new User();
        user.setId(userId);
        order.setUser(user);
        Tariff tariff = new Tariff();
        tariff.setId(body.getTariffId());
        order.setTariff(tariff);
        Place place = new Place();
        place.setId(body.getPlaceId());
        order.setPlace(place);
        order.setStatus(EStatus.IN_PROGRESS.getTitle());
        order.setPaymentStatus(EPaymentStatus.PAYMENT_NO.getTitle());
        order.setDateStart(body.getDate());
        order.setDateEnd(body.getDate().plusDays(body.getDaysCount()));
        order.setDaysCount(body.getDaysCount());
        order.setTotalPrice(0L);
        order = orderRepository.saveAndFlush(order);
        tariff = tariffRepository.getById(body.getTariffId());
        Long totalPrice = ((long) tariff.getPricePerDay() * body.getDaysCount());

        for(Long optionId : body.getOptionalIds()) {
            Option opt = optionRepository.findById(optionId).orElse(null);
            if (opt != null) {
                OrderOption oo = new OrderOption();
                oo.setCount(1L);
                oo.setOrder(order);
                oo.setOption(opt);
                orderOptionRepository.saveAndFlush(oo);
                totalPrice += opt.getPrice();
            }
        }
        order.setTotalPrice(totalPrice);
        order = orderRepository.saveAndFlush(order);

        return getOrderById(order.getId());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.setUser(userRepository.getById(order.getUser().getId()));
        OrderResponse resp = new OrderResponse();
        resp.setId(order.getId());
        resp.setDaysCount(order.getDaysCount());
        resp.setStatus(order.getStatus());
        resp.setDateStart(order.getDateStart());
        resp.setDateEnd(order.getDateEnd());
        resp.setTotalPrice((double) order.getTotalPrice()/100);
        resp.setPaymentStatus(order.getPaymentStatus());

        UserInfoDTO user = new UserInfoDTO();
        user.setId(order.getUser().getId());
        user.setFirstname(order.getUser().getFirstname());
        user.setLastname(order.getUser().getLastname());
        user.setUsername(order.getUser().getUsername());
        user.setPhone(order.getUser().getPhone());
        user.setEmail(order.getUser().getEmail());
        resp.setUser(user);

        order.setTariff(tariffRepository.getById(order.getTariff().getId()));
        TariffDTO tariff = new TariffDTO(order.getTariff().getId(), order.getTariff().getTitle(), order.getTariff().getDescription(), (double) order.getTariff().getPricePerDay() /100, order.getTariff().getPhoto());
        resp.setTariff(tariff);

        List<OrderOption> orderOptions = orderOptionRepository.findByOrderId(id);
        List<OptionDetailDTO> options = new ArrayList<>();
        for (OrderOption oo : orderOptions) {
            if (!oo.getOption().isDel()) {
                OptionDetailDTO od = new OptionDetailDTO();
                od.setId(oo.getOption().getId());
                od.setPrice((double) oo.getOption().getPrice()/100);
                od.setTitle(oo.getOption().getTitle());
                od.setCount(oo.getCount());

                options.add(od);
            }
        }
        resp.setAdditionalOptions(options);
        order.setPlace(placeRepository.getById(order.getPlace().getId()));
        PlacePreviewDTO place = new PlacePreviewDTO(order.getPlace().getId(), order.getPlace().getTitle(), order.getPlace().getCoordinateX(), order.getPlace().getCoordinateY());
        resp.setPlace(place);

        return resp;
    }

    public OrderPreviewsResponse getAllOrderPreviews(Long userId) {
        User user = new User();
        user.setId(userId);
        List<Order> orders = orderRepository.findByUser(user);

        OrderPreviewsResponse resp = new OrderPreviewsResponse();
        List<OrderPreviewDTO> dtos = new ArrayList<>();
        for (Order o : orders) {
            OrderPreviewDTO od = new OrderPreviewDTO();
            od.setId(o.getId());
            od.setStatus(o.getStatus());
            od.setPaymentStatus(o.getPaymentStatus());
            od.setDaysCount(Long.valueOf(o.getDaysCount()));
            od.setTotalPrice((double) o.getTotalPrice()/100);
            PlacePreviewDTOOrder place = new PlacePreviewDTOOrder();
            place.setId(o.getPlace().getId());
            place.setTitle(o.getPlace().getTitle());
            od.setPlace(place);
            dtos.add(od);
        }

        resp.setOrders(dtos);
        return resp;
    }

    public void changeStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        order.setStatus(EStatus.valueOf(status).getTitle());
        orderRepository.saveAndFlush(order);
    }
}
