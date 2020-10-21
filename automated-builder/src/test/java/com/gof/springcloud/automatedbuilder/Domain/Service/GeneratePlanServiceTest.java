package com.gof.springcloud.automatedbuilder.Domain.Service;

import com.gof.springcloud.automatedbuilder.Application.Mapper.ModelToGraphMapper;
import com.gof.springcloud.automatedbuilder.Application.Pulsar.Neo4jEntity;
import com.gof.springcloud.automatedbuilder.Application.Request.QueryBody;
import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel;
import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Activity;
import com.gof.springcloud.automatedbuilder.Application.Request.TravelPlanModel_Day;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntity;
import com.gof.springcloud.automatedbuilder.Domain.Graph.AbstractNodeEntityLinkedList;
import com.gof.springcloud.automatedbuilder.Domain.Graph.TravelActivity.Activity;
import com.gof.springcloud.automatedbuilder.Domain.Repository.IGeneratePlanRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.ws.rs.core.Application;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class GeneratePlanServiceTest {

	@Mock
	private IGeneratePlanRepository generatePlanRepository;

	@InjectMocks
	@Autowired
	private GeneratePlanService generatePlanService;

	@Before
	public void before(){
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAddGraph(){
		Activity activity = new Activity();
		activity.setCategory("food");

		AbstractNodeEntityLinkedList linkedList = new AbstractNodeEntityLinkedList();

		when(generatePlanRepository.Save(any(Activity.class), any(AbstractNodeEntityLinkedList.class))).thenReturn(activity);

		Activity activity1 = new Activity();
		activity1.setCategory("food");

		Assert.assertEquals(activity1, generatePlanService.SavePlanAsGraph(activity, linkedList));
	}

	@Test
	public void testAddGraphFromModel(){
		TravelPlanModel model = new TravelPlanModel();
		TravelPlanModel_Day day = new TravelPlanModel_Day();
		TravelPlanModel_Activity activity = new TravelPlanModel_Activity();
		activity.setCategory("food");
		activity.setReview("test");
		activity.setCost(2.00);
		activity.setTimeEnd(LocalDateTime.now());
		activity.setTimeStart(LocalDateTime.now());
		activity.setRating(3);
		activity.setLocation("test");
		day.getNodes().add(activity);
		model.getDays().add(day);

		Neo4jEntity entity = ModelToGraphMapper.convert(model);
		when(generatePlanRepository.Save(any(Activity.class), any(AbstractNodeEntityLinkedList.class))).thenReturn(entity.getEntity());

		Activity activity1 = new Activity();
		activity1.setCategory("food");

		Assert.assertEquals(activity1, generatePlanService.SavePlanAsGraph(entity.getEntity(), entity.getLinkedList()));
	}

	@Test
	public void testGeneratePlan(){
		List<AbstractNodeEntity> entityList = new ArrayList<>();
		QueryBody body = new QueryBody();
		when(generatePlanRepository.GeneratePlan(any(QueryBody.class))).thenReturn(entityList);

		Assert.assertEquals(new ArrayList<AbstractNodeEntity>(), generatePlanService.GeneratePlan(body));
	}
}
