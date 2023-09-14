package com.ojavali.doisrponto;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   /* coloca aqui o crud */
}