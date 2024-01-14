package org.example.jpql;
import javax.persistence.*;
import java.util.List;
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member = new Member();
            /*member.setUsername("member1");*/
            member.setUsername("회원1");
            member.setAge(10);
            member.setTeam(teamA);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            Member member2 = new Member();
            /*member.setUsername("member1");*/
            member2.setUsername("회원2");
            member2.setAge(11);
            member2.setTeam(teamA);
            member2.setType(MemberType.ADMIN);
            em.persist(member2);

            Member member3 = new Member();
            /*member.setUsername("member1");*/
            member3.setUsername("회원3");
            member3.setAge(12);
            member3.setTeam(teamB);
            member3.setType(MemberType.ADMIN);
            em.persist(member3);

            /*em.flush();
            em.clear();
*/
            //Flush 자동 호출 commit, query
            int resultCount = em.createQuery("update Member m set m.age = 20")
                    .executeUpdate();
            em.clear();
            Member findMember = em.find(Member.class, member.getId());
            //그래서 clear로 영속성 컨텍스트 비우고 디비에서 새로 가져와라.
            System.out.println("findMember = " + findMember.getAge());
            System.out.println("member = " + member.getAge());
            System.out.println("member = " + member2.getAge());
            System.out.println("member = " + member3.getAge());
            System.out.println("resultCount = " + resultCount); //영속성 컨텍스트와 불일치

            /*String query = "select m" +
                    " from Member m";*/ //fetch join이 답!

           /* String query = "select distinct t From Team t join fetch t.members";
            List<Team> resultList1 = em.createQuery(query, Team.class)
                    .getResultList();

            for (Team team : resultList1) {
                System.out.println("team = " + team.getName() + " | " + team.getMembers().size());
                for(Member member1 : team.getMembers()) {
                    System.out.println("  -> member = " + member1);
                }
            }*/

            //List<Member> resultList = em.createQuery(query, Member.class).getResultList();
           /* for (Member member1 : resultList) {
                System.out.println("member1 = " + member1.getUsername() + ", " +member1.getTeam().getName() );
                //회원1, 팀A(sql)
                //회원2, 팀A(1차 캐시)
                //회원3, 팀B(sql)
                //회원 100명 -> N+1 문제
                //:fetch join이 답이다.
            }*/

            /*String query = "select " +
                    "case when m.age <= 10 then '학생요금'" +
                    "     when m.age >= 60 then '경로요금'" +
                    "     else '일반요금' " + "end " +
                    "from Member m";*/
            //String query = "select coalesce(m.username, '이름 없는 회원') from Member m ";
            //String query = "select nullif(m.username, '관리자') as username from Member m";

            //String query = "select concat('a' , 'b') From Member m";
           /* String query = "select substring(m.username ,1, 2) From Member m";

            List<String> resultList = em.createQuery(query, String.class).getResultList();
            for (String s : resultList) {
                System.out.println("s = " + s);
            }*/

            /*String query = "select m.username, 'HELLO', TRUE from Member m " +
                    "where m.type = org.example.jpql.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query)
                    .getResultList();

            for (Object[] objects : result) {
                System.out.println("objects = " + objects[0]);
                System.out.println("objects = " + objects[1]);
                System.out.println("objects = " + objects[2]);
            }
            */
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
