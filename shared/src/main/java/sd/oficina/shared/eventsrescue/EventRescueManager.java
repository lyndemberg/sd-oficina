package sd.oficina.shared.eventsrescue;

import com.fasterxml.jackson.databind.ObjectMapper;
import sd.oficina.shared.model.EventRescue;
import sd.oficina.shared.model.ServiceEnum;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventRescueManager {

    private final ServiceEnum service;

    private final EntityManager emRescue;
    private final EntityManager emService;

    private final Executor executor;

    public EventRescueManager(ServiceEnum service, EntityManager emService) {
        this(service, emService, 1);
    }

    public EventRescueManager(ServiceEnum service, EntityManager emService, int numThreads) {
        this.service = service;

        this.emRescue = Persistence
                .createEntityManagerFactory("shared-persistence")
                .createEntityManager();

        this.emService = emService;

        this.executor = Executors.newFixedThreadPool(numThreads);
    }

    public void executeRescueEvents() {

        final ObjectMapper mapper = new ObjectMapper();

        this.emRescue
                .createNativeQuery("SELECT * FROM EventRescue WHERE service ILIKE #serviceName ORDER BY id", EventRescue.class)
                .setParameter("serviceName", this.service.toString())
                .getResultList()
                .forEach(eventRescue -> {
                    System.out.println("Finded EventResue -> " + eventRescue);
                    executor.execute(() -> processEventRescue((EventRescue) eventRescue, mapper));

                    emRescue.getTransaction().begin();
                    emRescue.remove(eventRescue);
                    emRescue.getTransaction().commit();

                });


    }

    private void processEventRescue(EventRescue eventRescue, ObjectMapper mapper) {

        try {
            Class<?> clazz = Class.forName("sd.oficina.shared.model." + eventRescue.getService().toString().toLowerCase() + "." + eventRescue.getEntity());

            emService.getTransaction().begin();

            switch (eventRescue.getAction()) {
                case INSERT:
                    emService.persist(
                            mapper.readValue(eventRescue.getPayload(), clazz)
                    );
                    break;
                case UPDATE:
                    emService.merge(
                            mapper.readValue(eventRescue.getPayload(), clazz)
                    );
                    break;
                case DELETE:

                    Object object = mapper.readValue(eventRescue.getPayload(), clazz);
                    Method getId = clazz.getDeclaredMethod("getId");
                    getId.setAccessible(true);

                    emService.remove(
                            emService.find(clazz, getId.invoke(object))
                    );

                    break;
                default:
                    System.out.println("EventRescueManager: Action not managed for this Object: " + eventRescue);
            }

            emService.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();

            emService.getTransaction().rollback();
        }

    }

}
