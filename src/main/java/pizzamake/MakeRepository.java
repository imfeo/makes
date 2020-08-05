package pizzamake;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface MakeRepository extends PagingAndSortingRepository<Make, Long>{

    public Make findByOrderId(long orderId);

}