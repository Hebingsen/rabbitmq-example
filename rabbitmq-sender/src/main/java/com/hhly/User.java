package com.hhly;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1565683504107583197L;
	private Long id;
	private String name;
	private String sex;
	
}
