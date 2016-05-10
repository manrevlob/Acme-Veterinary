package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, Integer> {

}
