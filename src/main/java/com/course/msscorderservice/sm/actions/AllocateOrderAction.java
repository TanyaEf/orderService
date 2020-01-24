package com.course.msscorderservice.sm.actions;

import com.course.msscorderservice.config.JmsConfig;
import com.course.msscorderservice.domain.BeerOrder;
import com.course.msscorderservice.domain.BeerOrderEventEnum;
import com.course.msscorderservice.domain.BeerOrderStatusEnum;
import com.course.msscorderservice.repositories.BeerOrderRepository;
import com.course.msscorderservice.services.BeerOrderManagerImpl;
import com.course.msscorderservice.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AllocateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.ORDER_ID_HEADER);
        BeerOrder beerOrder = beerOrderRepository.findOneById(UUID.fromString(beerOrderId));

        jmsTemplate.convertAndSend(JmsConfig.ALLOCATE_ORDER_QUEUE,
                beerOrderMapper.beerOrderToDto(beerOrder));

        log.debug("Sent Allocation Request for order id: " + beerOrderId);
    }
}
