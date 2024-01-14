package org.example.jpql;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();
            String query = "select m from Member m left join Team t on m.username= t.name";
            List<Member> result = em.createQuery(query, Member.class)
                    .getResultList();

            /*List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();*/



            /*System.out.println("result.size = " + resultList.size());
            for (Member member1 : resultList) {
                System.out.println("member = " + member1);
            }*/

            /*TypedQuery<Member> query1 = em.createQuery("select m From Member m", Member.class); //반환 타입 명확할때
            TypedQuery<String> query2 = em.createQuery("select m.username From Member m", String.class);//반환 타입 명확할때
            List<MemberDTO> resultList1 = em.createQuery("select new org.example.jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
            MemberDTO memberDTO = resultList1.get(0);
            System.out.println("memberDTO.getUsername() = " + memberDTO.getUsername());
            System.out.println("memberDTO.getAge() = " + memberDTO.getAge());*/
            /*
            Member reuslt = em.createQuery("select m from Member m where m.username = :username", Member.class)
                    .setParameter("username", "member1")
                    .getSingleResult();
            System.out.println("singleResult = " + reuslt.getUsername());
            Member result2 = query1.getSingleResult();
            //Spring Data JPA ->
            System.out.println("result = " + result2);
            List<Member> resultList = query1.getResultList();
            for (Member member1 : resultList) {
                System.out.println("member1 = " + member1);
            }*/
            //em.persist(member);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {

        }

    }
}
