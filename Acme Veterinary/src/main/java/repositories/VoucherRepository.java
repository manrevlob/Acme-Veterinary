package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Customer;
import domain.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

	@Query("select v from Voucher v where v.code = ?1")
	Voucher findByCode(String code);

	@Query("select count(o) from Order o where o.customer = ?1 and o.voucher = ?2")
	Integer getVoucherIsUsedByPrincipal(Customer principal, Voucher voucher);

}
