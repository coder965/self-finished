/*
 * Copyright 2011-2014 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.data.redis.core;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.core.convert.converter.Converter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisZSetCommands.Tuple;

/**
 * Default implementation of {@link ZSetOperations}.
 * 
 * @author Costin Leau
 * @author Christoph Strobl
 * @author Thomas Darimont
 * @author David Liu
 */
class DefaultZSetOperations<K, V> extends AbstractOperations<K, V> implements ZSetOperations<K, V> {

	DefaultZSetOperations(RedisTemplate<K, V> template) {
		super(template);
	}

	@Override
	public Boolean add(final K key, final V value, final double score) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		return execute(new RedisCallback<Boolean>() {

			@Override
			public Boolean doInRedis(RedisConnection connection) {
				return connection.zAdd(rawKey, score, rawValue);
			}
		}, true);
	}

	@Override
	public Long add(K key, Set<TypedTuple<V>> tuples) {
		final byte[] rawKey = rawKey(key);
		final Set<Tuple> rawValues = rawTupleValues(tuples);

		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zAdd(rawKey, rawValues);
			}
		}, true);
	}

	@Override
	public Double incrementScore(K key, V value, final double delta) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(value);

		return execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) {
				return connection.zIncrBy(rawKey, delta, rawValue);
			}
		}, true);
	}

	@Override
	public Long intersectAndStore(K key, K otherKey, K destKey) {
		return intersectAndStore(key, Collections.singleton(otherKey), destKey);
	}

	@Override
	public Long intersectAndStore(K key, Collection<K> otherKeys, K destKey) {
		final byte[][] rawKeys = rawKeys(key, otherKeys);
		final byte[] rawDestKey = rawKey(destKey);
		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zInterStore(rawDestKey, rawKeys);
			}
		}, true);
	}

	@Override
	public Set<V> range(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRange(rawKey, start, end);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<V> reverseRange(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRevRange(rawKey, start, end);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> rangeWithScores(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRangeWithScores(rawKey, start, end);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeWithScores(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRevRangeWithScores(rawKey, start, end);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Set<V> rangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRangeByScore(rawKey, min, max);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<V> rangeByScore(K key, final double min, final double max, final long offset, final long count) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRangeByScore(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<V> reverseRangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRevRangeByScore(rawKey, min, max);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<V> reverseRangeByScore(K key, final double min, final double max, final long offset, final long count) {
		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRevRangeByScore(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRangeByScoreWithScores(rawKey, min, max);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> rangeByScoreWithScores(K key, final double min, final double max, final long offset,
			final long count) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRangeByScoreWithScores(rawKey, min, max, offset, count);
			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRevRangeByScoreWithScores(rawKey, min, max);

			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Set<TypedTuple<V>> reverseRangeByScoreWithScores(K key, final double min, final double max, final long offset,
			final long count) {
		final byte[] rawKey = rawKey(key);

		Set<Tuple> rawValues = execute(new RedisCallback<Set<Tuple>>() {

			@Override
			public Set<Tuple> doInRedis(RedisConnection connection) {
				return connection.zRevRangeByScoreWithScores(rawKey, min, max, offset, count);

			}
		}, true);

		return deserializeTupleValues(rawValues);
	}

	@Override
	public Long rank(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				Long zRank = connection.zRank(rawKey, rawValue);
				return (zRank != null && zRank.longValue() >= 0 ? zRank : null);
			}
		}, true);
	}

	@Override
	public Long reverseRank(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				Long zRank = connection.zRevRank(rawKey, rawValue);
				return (zRank != null && zRank.longValue() >= 0 ? zRank : null);
			}
		}, true);
	}

	@Override
	public Long remove(K key, Object... values) {
		final byte[] rawKey = rawKey(key);
		final byte[][] rawValues = rawValues(values);

		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zRem(rawKey, rawValues);
			}
		}, true);
	}

	@Override
	public Long removeRange(K key, final long start, final long end) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zRemRange(rawKey, start, end);
			}
		}, true);
	}

	@Override
	public Long removeRangeByScore(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zRemRangeByScore(rawKey, min, max);
			}
		}, true);
	}

	@Override
	public Double score(K key, Object o) {
		final byte[] rawKey = rawKey(key);
		final byte[] rawValue = rawValue(o);

		return execute(new RedisCallback<Double>() {

			@Override
			public Double doInRedis(RedisConnection connection) {
				return connection.zScore(rawKey, rawValue);
			}
		}, true);
	}

	@Override
	public Long count(K key, final double min, final double max) {
		final byte[] rawKey = rawKey(key);

		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zCount(rawKey, min, max);
			}
		}, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.ZSetOperations#size(java.lang.Object)
	 */
	@Override
	public Long size(K key) {
		return zCard(key);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.ZSetOperations#zCard(java.lang.Object)
	 */
	@Override
	public Long zCard(K key) {

		final byte[] rawKey = rawKey(key);
		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zCard(rawKey);
			}
		}, true);
	}

	@Override
	public Long unionAndStore(K key, K otherKey, K destKey) {
		return unionAndStore(key, Collections.singleton(otherKey), destKey);
	}

	@Override
	public Long unionAndStore(K key, Collection<K> otherKeys, K destKey) {
		final byte[][] rawKeys = rawKeys(key, otherKeys);
		final byte[] rawDestKey = rawKey(destKey);
		return execute(new RedisCallback<Long>() {

			@Override
			public Long doInRedis(RedisConnection connection) {
				return connection.zUnionStore(rawDestKey, rawKeys);
			}
		}, true);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.data.redis.core.ZSetOperations#scan(java.lang.Object, org.springframework.data.redis.core.ScanOptions)
	 */
	@Override
	public Cursor<TypedTuple<V>> scan(K key, final ScanOptions options) {

		final byte[] rawKey = rawKey(key);
		Cursor<Tuple> cursor = execute(new RedisCallback<Cursor<Tuple>>() {

			@Override
			public Cursor<Tuple> doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.zScan(rawKey, options);
			}
		}, true);

		return new ConvertingCursor<Tuple, TypedTuple<V>>(cursor, new Converter<Tuple, TypedTuple<V>>() {

			@Override
			public TypedTuple<V> convert(Tuple source) {
				return deserializeTuple(source);
			}
		});
	}

	public Set<byte[]> rangeByScore(K key, final String min, final String max) {

		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRangeByScore(rawKey, min, max);
			}
		}, true);

		return rawValues;
	}

	public Set<byte[]> rangeByScore(K key, final String min, final String max, final long offset, final long count) {

		final byte[] rawKey = rawKey(key);

		Set<byte[]> rawValues = execute(new RedisCallback<Set<byte[]>>() {

			@Override
			public Set<byte[]> doInRedis(RedisConnection connection) {
				return connection.zRangeByScore(rawKey, min, max, offset, count);
			}
		}, true);

		return rawValues;
	}
}
