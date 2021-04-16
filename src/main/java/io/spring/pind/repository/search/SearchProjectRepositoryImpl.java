package io.spring.pind.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import io.spring.pind.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

public class SearchProjectRepositoryImpl extends QuerydslRepositorySupport implements SearchProjectRepository {

    public SearchProjectRepositoryImpl() {
        super(Project.class);
    }

    @Override
    public Page<Object> searchProjectListWithPagination(String type, String keyword, Pageable pageable) {

        QProject project = QProject.project;
        QParticipate participate = QParticipate.participate;
        QParticipate participateTmp = new QParticipate("participateTmp");
        QRegion region = QRegion.region;
        QSubject subject = QSubject.subject;
        QFile file = QFile.file;
        QMember member = QMember.member;

        JPQLQuery<Project> jpqlQuery = from(project);
        jpqlQuery.leftJoin(subject).on(project.subject.eq(subject));
        jpqlQuery.leftJoin(region).on(project.region.eq(region));
        jpqlQuery.leftJoin(file).on(project.file.eq(file));
        jpqlQuery.leftJoin(participate).on(participate.project.eq(project));
        jpqlQuery.leftJoin(participateTmp).on(participateTmp.project.eq(project));
        jpqlQuery.leftJoin(member).on(participateTmp.member.eq(member));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(project, subject, region, file, member, participate.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        BooleanExpression expression = participateTmp.role.eq(ParticipateRole.LEADER);
        booleanBuilder.and(expression);

        if (type != null){
            String[] typeArr = type.split("-");
            BooleanBuilder conditionBuilder = new BooleanBuilder();
            for(String t :typeArr){
                switch(t){
                    case "title":
                        conditionBuilder.or(project.title.contains(keyword));
                        break;
                    case "content":
                        conditionBuilder.or(project.content.contains(keyword));
                        break;
                    case "leader":
                        conditionBuilder.or(member.name.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

        Sort sort = pageable.getSort();
        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC: Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder(Project.class, "project");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });
        tuple.groupBy(project);

        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount();

        return new PageImpl<Object>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
