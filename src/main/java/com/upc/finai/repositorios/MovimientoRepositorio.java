package com.upc.finai.repositorios;

import com.upc.finai.entidades.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findByUsuarioIdOrderByCreadoEnDesc(Long idUsuario);

    List<Movimiento> findByUsuarioIdAndTipoOrderByCreadoEnDesc(Long idUsuario, String tipo);

    @Query("select coalesce(sum(m.monto), 0) from Movimiento m where m.usuario.id = :idUsuario and upper(m.tipo) = upper(:tipo)")
    BigDecimal sumarPorUsuarioYTipo(@Param("idUsuario") Long idUsuario, @Param("tipo") String tipo);
}
