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
import org.example.repository.OptionRepository;
import org.example.repository.OrderOptionRepository;
import org.example.repository.OrderRepository;
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
    OptionRepository optionRepository;

    public OrderResponse createOrder(Long userId, OrderCreateRequest body) {
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
        order = orderRepository.saveAndFlush(order);
        order.setStatus(EStatus.IN_PROGRESS.getTitle());
        order.setPaymentStatus(EPaymentStatus.PAYMENT_NO.getTitle());

        for(Long optionId : body.getOptionalIds()) {
            Option opt = optionRepository.findById(optionId).orElse(null);
            if (opt != null) {
                OrderOption oo = new OrderOption();
                oo.setCount(1L);
                oo.setOrder(order);
                oo.setOption(opt);
            }
        }

        return getOrderById(order.getId());
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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

        TariffDTO tariff = new TariffDTO(resp.getTariff().getId(), resp.getTariff().getTitle(), resp.getTariff().getDescription(), resp.getTariff().getPricePerDay(), resp.getTariff().getPhoto());
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
