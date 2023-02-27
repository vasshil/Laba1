package com.example.laba1.service.impl

import com.example.laba1.data.Client
import com.example.laba1.repository.ClientRepository
import org.springframework.data.domain.Example
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.repository.query.FluentQuery
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function

@Service
class ClientServiceImpl: ClientRepository {
//    override fun searchClientsByName(name: String): List<Client> {
//
//    }

    override fun <S : Client?> save(entity: S): S {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> saveAll(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findAll(): MutableList<Client> {
        TODO("Not yet implemented")
    }

    override fun findAll(sort: Sort): MutableList<Client> {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> findAll(example: Example<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> findAll(example: Example<S>, sort: Sort): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun findAll(pageable: Pageable): Page<Client> {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> findAll(example: Example<S>, pageable: Pageable): Page<S> {
        TODO("Not yet implemented")
    }

    override fun findAllById(ids: MutableIterable<Long>): MutableList<Client> {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> count(example: Example<S>): Long {
        TODO("Not yet implemented")
    }

    override fun delete(entity: Client) {
        TODO("Not yet implemented")
    }

    override fun deleteAllById(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll(entities: MutableIterable<Client>) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> findOne(example: Example<S>): Optional<S> {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> exists(example: Example<S>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <S : Client?, R : Any?> findBy(
        example: Example<S>,
        queryFunction: Function<FluentQuery.FetchableFluentQuery<S>, R>
    ): R {
        TODO("Not yet implemented")
    }

    override fun flush() {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> saveAndFlush(entity: S): S {
        TODO("Not yet implemented")
    }

    override fun <S : Client?> saveAllAndFlush(entities: MutableIterable<S>): MutableList<S> {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch(entities: MutableIterable<Client>) {
        TODO("Not yet implemented")
    }

    override fun deleteAllInBatch() {
        TODO("Not yet implemented")
    }

    override fun deleteAllByIdInBatch(ids: MutableIterable<Long>) {
        TODO("Not yet implemented")
    }

    override fun getReferenceById(id: Long): Client {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Client {
        TODO("Not yet implemented")
    }

    override fun getOne(id: Long): Client {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Optional<Client> {
        TODO("Not yet implemented")
    }


}