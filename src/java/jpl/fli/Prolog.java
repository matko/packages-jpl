package jpl.fli;

import jpl.JPL;

/**
 * This class consists only of constants (static finals) and static native methods. The constants and methods defined herein are in (almost) strict 1-1 correspondence with the functions in the Prolog
 * FLI by the same name (except without the PL_, SQ_, etc. prefixes).
 * <p>
 * 
 * See the file jpl.c for the native (ANSI C) implementations of these methods. Refer to your local Prolog FLI documentations for the meanings of these methods, and observe the following:
 * <p>
 * 
 * <menu>
 * <li>The types and signatures of the following methods are almost in 1-1 correspondence with the Prolog FLI. The Prolog types term_t, atom_t, functor_t, etc. are mirrored in this package with
 * classes by the same name, making the C and Java uses of these interfaces similar.</li>
 * <li>As term_t, functor_t, etc. types are Java classes, they are passed to these methods <b>by value</b>; however, calling these methods on such class instances does have side effects. In general,
 * the value fields of these instances will be modified, in much the same way the term_t, functor_t, etc. Prolog instances would be modified.</li>
 * <li>The exceptions to this rule occur when maintaining the same signature would be impossible, e.g., when the Prolog FLI functions require <i>pointers</i>; in this case, the signatures have been
 * modified to take *Holder classes (Int, Double, String, etc.), to indicate a call by reference parameter.
 * <li>Functions which take variable-length argument lists in C take arrays in Java; from Java 1.1 onwards, anonymous arrays can be used e.g. Term[] { new Atom("a"), new Atom ("b") } </menu>
 * 
 * <hr>
 * <i> Copyright (C) 1998 Fred Dushin
 * <p>
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Library Public License as published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 * <p>
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Library Public License for more details.
 * <p>
 * </i>
 * <hr>
 * 
 * @author Fred Dushin <fadushin@syr.edu>
 * @version $Revision$
 */
public final class Prolog {
	static {
		JPL.loadNativeLibrary();
	}

	/* term types */
	public static final int VARIABLE = 1; // PL_VARIABLE
	public static final int ATOM = 2; // PL_ATOM
	public static final int INTEGER = 3; // PL_INTEGER
	public static final int FLOAT = 4; // PL_FLOAT
	public static final int STRING = 5; // PL_STRING
	public static final int COMPOUND = 6; // PL_TERM

	public static final int LIST_NIL = 7; // PL_NIL
	public static final int LIST_PAIR = 9; // PL_LIST_PAIR
	public static final int BLOB = 8; // PL_BLOB
	public static final int DICT = 44; // PL_DICT

	public static final int JBOOLEAN = 101;
	public static final int JREF = 102;
	public static final int JVOID = 103;

	/**
	 * @deprecated use Prolog.COMPOUND
	 */
	public static final int TERM = 6;

	public static final int succeed = 1;
	public static final int fail = 0;

	/* query flags */
	public static final int Q_NORMAL = 0x02;
	public static final int Q_NODEBUG = 0x04;
	public static final int Q_CATCH_EXCEPTION = 0x08;
	public static final int Q_PASS_EXCEPTION = 0x10;

	/* conversion flags */
	public static final int CVT_ATOM = 0x0001;
	public static final int CVT_STRING = 0x0002;
	public static final int CVT_LIST = 0x0004;
	public static final int CVT_INTEGER = 0x0008;
	public static final int CVT_FLOAT = 0x0010;
	public static final int CVT_VARIABLE = 0x0020;
	public static final int CVT_NUMBER = (CVT_INTEGER | CVT_FLOAT);
	public static final int CVT_ATOMIC = (CVT_NUMBER | CVT_ATOM | CVT_STRING);
	public static final int CVT_ALL = 0x00ff;
	public static final int BUF_DISCARDABLE = 0x0000;
	public static final int BUF_RING = 0x0100;
	public static final int BUF_MALLOC = 0x0200;

	/* syntax flavours */
	public static final int SYNTAX_TRADITIONAL = 202;
	public static final int SYNTAX_MODERN = 203;

	/* new, for revised term_t-to-Variable stuff */
	public static native int compare(term_t t1, term_t t2); // returns -1, 0 or 1

	/* Creating and destroying term-refs */
	public static native term_t new_term_ref();

	public static native term_t new_term_refs(int n);

	public static native term_t copy_term_ref(term_t from); // NOT USED

	/* Constants */
	public static native atom_t new_atom(String s);

	public static native String atom_chars(atom_t a);

	public static native functor_t new_functor(atom_t f, int a);

	public static native void unregister_atom(atom_t a); // called from atom_t's finalize()

	/* Get Java-values from Prolog terms */
	public static native boolean get_atom_chars(term_t t, StringHolder a);

	public static native boolean get_string_chars(term_t t, StringHolder s);

	public static native boolean get_integer(term_t t, Int64Holder i);

	public static native boolean get_float(term_t t, DoubleHolder d);

	public static native boolean get_name_arity(term_t t, StringHolder name, IntHolder arity);

	public static native boolean get_arg(int index, term_t t, term_t a);

	public static native String object_to_tag(Object obj);

	public static native Object tag_to_object(String tag); // 29/May/2008

	public static native boolean is_tag(String tag); // 30/May/2008

	/* Verify types */
	public static native int term_type(term_t t);

	/* Assign to term-references */
	public static native void put_variable(term_t t);

	public static native void put_integer(term_t t, long i);

	public static native void put_float(term_t t, double f);

	public static native void put_term(term_t t1, term_t t2);

	public static native void put_jref(term_t t, Object ref);

	public static native void put_atom_chars(term_t t, String name);

	public static native void put_nil(term_t t);

	/* ... */
	public static native void cons_functor_v(term_t h, functor_t fd, term_t a0);

	// predicates:
	public static native predicate_t predicate(String name, int arity, String module);

	// querying (general):
	public static native qid_t open_query(module_t m, int flags, predicate_t pred, term_t t0);

	public static native boolean next_solution(qid_t qid);

	public static native void close_query(qid_t qid);

	// modules:
	public static native module_t new_module(atom_t name);

	// exceptions:
	public static native term_t exception(qid_t qid);

	// initialisation:
	public static native String[] get_default_init_args();

	public static native boolean set_default_init_args(String argv[]);

	public static native boolean initialise();

	public static native String[] get_actual_init_args();

	public static native void halt(int status);

	// thread & engine management:
	public static native int thread_self();

	public static native engine_t attach_pool_engine();

	public static native int release_pool_engine();

	public static native engine_t current_engine();

	public static native boolean current_engine_is_pool();

	public static native int attach_engine(engine_t e);

	// misc.
	public static native String get_c_lib_version();

	// not yet working:
	public static native int action_abort();

	// revived 17/Jun/2008:
	public static native fid_t open_foreign_frame();

	public static native void discard_foreign_frame(fid_t cid);

	// syntax enquiry:
	public static native int get_syntax();
}
