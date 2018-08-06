package co.bdigital.cmm.jpa.service.impl;

import javax.persistence.EntityManager;

import co.bdigital.cmm.jpa.exception.EnumJPAException;
import co.bdigital.cmm.jpa.exception.JPAException;
import co.bdigital.cmm.jpa.model.Bono;
import co.bdigital.cmm.jpa.services.VoucherJPAService;

public class VoucherJPAServiceIMPL implements VoucherJPAService {
private static final String BONO_ID="1";
	
	
	@Override
	public Bono getVoucher(EntityManager em) throws JPAException {
		final String metodo = "getVoucher";
		try {
		Bono voucher =  em.find(Bono.class,BONO_ID);
		if(voucher==null)
		{   voucher= new Bono();
			voucher.setValor(0);
		}
		return voucher;
		} catch (Exception e) {
			throw new JPAException(EnumJPAException.DB_ERROR, this.getClass()
					.getName() + metodo, e);
		}

	}

}
