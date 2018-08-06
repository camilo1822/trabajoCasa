package co.bdigital.cmm.jpa.services;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Bono;

public interface VoucherJPAService {

	/**
	 * Método que retorna un bono activo
	 * @param em
	 * @return
	 * @throws JPAException
	 */
public Bono getVoucher(EntityManager em) throws JPAException;
}
