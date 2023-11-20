package com.kh.totalJPASample.repository;

import com.kh.totalJPASample.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional; //

@Repository
// 네이밍 규칙에 의해 API를 작성하면 그에 맞는 쿼리를 하이버네이트가 구현
// 네이밍 규칙에 의해 API를 작성(JPA 구간)
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email); //Optional은 null 값이 들어오는 걸 방지하기 위해서
    // 인터페이스 구현부가 있으면 안된다. 상속받는 애가 구현
    Member findByPassword(String pwd);
    Member findByEmailAndPassword(String email, String pwd);
    boolean existsByEmail(String email);

}
