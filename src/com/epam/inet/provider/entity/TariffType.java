package com.epam.inet.provider.entity;

/**
 * Tariff type
 */
public enum TariffType {
	
	LTEINET(1), ETHERNETINET(2), CELLPHONE(3), IPTV(4);

	/**
	 * Type id
	 */
	private int id;

	/**
	 * Enum constructor
	 * @param id
	 */
	TariffType(int id){
		this.id = id;
	}

    /**
     * Get tariff id
     * @return
     */
    public int getId() {
        return id;
    }

	/**
	 * Find type by id
	 * @param id
	 * @return
	 */
	public static TariffType findById(int id){
		for (TariffType type: TariffType.values()){
			if (type.id == id){
				return type;
			}
		}

	   return null;
	}

	public String getDisplayName(){
		return this.name().toLowerCase();
	}
}
